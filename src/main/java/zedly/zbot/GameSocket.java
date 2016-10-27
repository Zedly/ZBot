/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import zedly.zbot.network.CryptManager;
import zedly.zbot.network.Packet;
import zedly.zbot.network.PacketInputStream;
import zedly.zbot.network.PacketOutputStream;
import zedly.zbot.network.StreamState;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.clientbound.ClientBoundPacket;
import zedly.zbot.network.packet.clientbound.Packet23JoinGame;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;
import static java.lang.Thread.sleep;
import zedly.zbot.network.Packet.Packet00Disconnect;
import zedly.zbot.network.packet.clientbound.Packet1ADisconnect;

/**
 *
 * @author Dennis
 */
public class GameSocket {

    private final int[] lastReadPackets = new int[16];
    private final int[] lastSentPackets = new int[16];
    private final Inflater inflater = new Inflater();
    private final ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
    private final ExtendedDataOutputStream dataBuffer = new ExtendedDataOutputStream(byteBuffer);
    private final InetSocketAddress serverAddress;
    private final Session session;

    private boolean compressionEnabled = false;
    private boolean sessionValid = false;
    private int readPacketCounter = 0;
    private int sentPacketCounter = 0;
    private Socket mainSocket;
    private ExtendedDataInputStream inputStream;
    private ExtendedDataOutputStream outputStream;
    private StreamState state = StreamState.LOGIN;
    private Packet23JoinGame packet23JoinGame;
    private Packet00Disconnect packet00Disconnect;

    public GameSocket(InetSocketAddress serverAddress, Session session) {
        this.serverAddress = serverAddress;
        this.session = session;
    }

    public synchronized boolean connect() {
        try {
            mainSocket = new Socket();
            mainSocket.setSoTimeout(60000);
            mainSocket.connect(serverAddress);
            inputStream = new PacketInputStream(mainSocket.getInputStream());
            outputStream = new PacketOutputStream(mainSocket.getOutputStream());
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public synchronized boolean handshake() {
        Packet.Packet00Handshake pack = new Packet.Packet00Handshake(210, serverAddress.getAddress().getHostAddress(), serverAddress.getPort(), 2);
        Packet.Packet00LoginStart loginStart = new Packet.Packet00LoginStart(session.getActualUsername());

        try {
            writePacket(pack);
            writePacket(loginStart);

            while (true) {
                int len = inputStream.readVarInt();
                if (compressionEnabled) {
                    int clen = inputStream.readVarInt();
                    //System.out.println("Login Debug: CLen " + clen);
                }

                int op = inputStream.readVarInt();
                System.out.println("Op: " + op);
                switch (op) {
                    case 1:
                        //System.out.println("Exchanging keys...");
                        Packet.Packet01EncryptionRequest encReq = new Packet.Packet01EncryptionRequest();
                        encReq.readPacket(inputStream, 0);
                        //System.out.println("Server Public Key " + Util.bytesToHex((encReq.getPublicKey())));
                        PublicKey serverPublicKey = CryptManager.func_75896_a(encReq.getPublicKey());
                        byte[] aesKeyBytes = "icetubeisafoggot".getBytes();
                        SecretKey secretKey = new SecretKeySpec(aesKeyBytes, "AES");
                        byte[] serverhash = CryptManager.func_75895_a(encReq.getServerID(), serverPublicKey, secretKey);
                        String serverHashString = CryptManager.getHexString(serverhash);
                        //System.out.println("Server Hash " + s_serverhash);
                        byte[] sharedSecret = CryptManager.func_75894_a(serverPublicKey, secretKey.getEncoded());
                        byte[] verifyToken = CryptManager.func_75894_a(serverPublicKey, encReq.getVerifyToken());

                        if (session.isOnlineMode() && !session.authenticateConnection(serverHashString)) {
                            sessionValid = false;
                            return false;
                        }

                        writePacket(new Packet.Packet01EncryptionResponse(sharedSecret, verifyToken));
                        InputStream is = CryptManager.decryptInputStream(secretKey, mainSocket.getInputStream());
                        OutputStream os = CryptManager.encryptOuputStream(secretKey, mainSocket.getOutputStream());
                        inputStream = new PacketInputStream(is, StreamState.PLAY);
                        outputStream = new PacketOutputStream(os, StreamState.PLAY);
                        break;
                    case 0:
                        packet00Disconnect = new Packet.Packet00Disconnect();
                        packet00Disconnect.readPacket(inputStream, 0);
                        int slen = inputStream.readVarInt();
                        byte[] stuff = new byte[slen];
                        inputStream.readFully(stuff);
                        String reason = new String(stuff);
                        System.out.println("Disconneted: " + reason);
                        if (reason.contains("40")) {
                            sessionValid = false;
                            return false;
                        }
                        break;
                    case 2:
                        Packet.Packet02LoginSuccess p2 = new Packet.Packet02LoginSuccess();
                        p2.readPacket(inputStream, 0);
                        break;
                    case 3:
                        Packet.Packet03SetCompression p3 = new Packet.Packet03SetCompression();
                        p3.readPacket(inputStream, 0);
                        compressionEnabled = true;
                        //System.out.println("Set Compression Threshold " + p3.getThreshold());
                        break;
                    case 35:
                        packet23JoinGame = new Packet23JoinGame();
                        packet23JoinGame.readPacket(inputStream, 0);
                        state = StreamState.PLAY;
                        return true;
                    default:
                        System.out.println("\rLogging in.. [FAIL]");
                        System.err.println("Protocol Error: Op " + op);
                        return false;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public synchronized Packet23JoinGame getJoinGamePacket() {
        return packet23JoinGame;
    }

    public synchronized Packet00Disconnect getDisconnectPacket() {
        return packet00Disconnect;
    }

    public synchronized ClientBoundPacket readPacket() {
        ClientBoundPacket newPacket;
        int op;
        try {
            int packetLength = inputStream.readVarInt();
            //System.out.println("Play Debug: Len " + len);
            if (compressionEnabled) {
                int uncompressedLength = inputStream.readVarInt();
                //System.out.println("Play Debug: CLen " + clen);
                if (uncompressedLength != 0) {
                    //System.out.println("Compressed Debug: Len " + len + " clen " + clen);
                    byte[] cdata = new byte[packetLength - getVarIntLength(uncompressedLength)];
                    byte[] data = new byte[uncompressedLength];
                    inputStream.readFully(cdata);
                    inflater.setInput(cdata);
                    inflater.inflate(data);
                    inflater.reset();

                    ExtendedDataInputStream bis = new ExtendedDataInputStream(new ByteArrayInputStream(data));

                    op = bis.readVarInt();
                    lastReadPackets[(readPacketCounter++) % 16] = op;
                    //System.out.println("Play Debug: Op " + Integer.toHexString(op));
                    newPacket = Packet.getInstanceClientBound(op, state);
                    newPacket.readPacket(bis, uncompressedLength - 1);
                    if (bis.available() != 0) {
                        System.err.println("Dropped " + bis.available() + " bytes in compressed packet " + Integer.toHexString(op) + " of size " + uncompressedLength + "!");
                    }
                    return newPacket;
                } else {
                    op = inputStream.readVarInt();
                    lastReadPackets[(readPacketCounter++) % 16] = op;
                    //System.out.println("Play Debug: Op " + Integer.toHexString(op));
                    newPacket = Packet.getInstanceClientBound(op, state);
                    newPacket.readPacket(inputStream, packetLength - 2);
                    return newPacket;
                }
            } else {
                op = inputStream.readVarInt();
                lastReadPackets[(readPacketCounter++) % 16] = op;
                //System.out.println("Play Debug: Op " + Integer.toHexString(op));
                newPacket = Packet.getInstanceClientBound(op, state);
                newPacket.readPacket(inputStream, packetLength - 1);
                return newPacket;
            }
        } catch (IOException | InstantiationException | IllegalAccessException | DataFormatException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public synchronized void writePacket(ServerBoundPacket p) {
        int size = p.getSize();
        int op = p.opCode();
        lastSentPackets[(sentPacketCounter++) % 16] = op;
        try {
            if (size == -1) {
                // Some packets do not know their own size yet. 
                // If they do not, simulate sending and count bytes.
                byteBuffer.reset();
                dataBuffer.writeVarInt(p.opCode());
                p.writePacket(dataBuffer);
                dataBuffer.flush();
                size = byteBuffer.size();
                outputStream.writeVarInt(size);
                if (compressionEnabled) {
                    outputStream.writeVarInt(0);
                }
                outputStream.write(byteBuffer.toByteArray());
            } else {
                outputStream.writeVarInt(size);
                outputStream.writeVarInt(0);
                p.writePacket(outputStream);
            }
        } catch (IOException ex) {
            // TODO: IOE while writing should happen while reading too. Ignore?
        }
    }

    public synchronized void reset() {
        for (int i = 0; i < 16; i++) {
            lastReadPackets[i] = 0;
            lastSentPackets[i] = 0;
            readPacketCounter = 0;
            sentPacketCounter = 0;
            compressionEnabled = false;
        }
    }

    private int getVarIntLength(int i) {
        int s = 1;
        while ((i >>= 7) != 0) {
            s++;
        }
        return s;
    }

}
