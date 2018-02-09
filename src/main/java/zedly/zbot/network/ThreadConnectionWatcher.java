package zedly.zbot.network;

import zedly.zbot.HTTP;
import zedly.zbot.network.Packet.Packet00Handshake;
import zedly.zbot.network.Packet.Packet00LoginStart;
import zedly.zbot.network.Packet.Packet01EncryptionRequest;
import zedly.zbot.network.Packet.Packet01EncryptionResponse;
import zedly.zbot.network.Packet.Packet02LoginSuccess;
import zedly.zbot.network.Packet.Packet03SetCompression;
import zedly.zbot.Session;
import zedly.zbot.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import zedly.zbot.GameContext;
import zedly.zbot.HTTP.HTTPResponse;
import zedly.zbot.network.packet.clientbound.Packet23JoinGame;

public class ThreadConnectionWatcher extends Thread {

    private final Session session;
    private final String serverIP;
    private final int serverPort;
    private final GameContext context;
    private Socket sock;
    private boolean keepAlive = true;

    public ThreadConnectionWatcher(GameContext context) {
        this.session = context.getSession();
        this.serverIP = context.getServerIp();
        this.serverPort = context.getServerPort();
        this.context = context;
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                boolean compressionEnabled = false;
                System.out.print("\rLogging in.. [#   ]");
                sock = new Socket();
                sock.setSoTimeout(60000);
                sock.connect(new InetSocketAddress(InetAddress.getByName(serverIP), serverPort));

                OutputStream os = sock.getOutputStream();
                InputStream is = sock.getInputStream();
                PacketInputStream dis = new PacketInputStream(is, StreamState.LOGIN);
                PacketOutputStream dos = new PacketOutputStream(os, StreamState.LOGIN);

                Packet00Handshake pack = new Packet00Handshake(340, serverIP, serverPort, 2);
                Packet00LoginStart loginStart = new Packet00LoginStart(session.getActualUsername());

                dos.writePacket(pack);
                dos.writePacket(loginStart);

                while (true) {
                    int len = dis.readVarInt();
                    //System.out.println("Login Debug: Len " + len);
                    if (compressionEnabled) {
                        int clen = dis.readVarInt();
                        //System.out.println("Login Debug: CLen " + clen);
                    }
                    int op = dis.readVarInt();
                    if (op == 1) {
                        System.out.print("\rLogging in.. [##  ]");
                        //System.out.println("Exchanging keys...");
                        Packet01EncryptionRequest encReq = new Packet01EncryptionRequest();
                        encReq.readPacket(dis, 0);
                        //System.out.println("Server Public Key " + Util.bytesToHex((encReq.getPublicKey())));
                        PublicKey key = CryptManager.func_75896_a(encReq.getPublicKey());
                        byte[] aes_key = "icetubeisafoggot".getBytes();
                        SecretKey secretKey = new SecretKeySpec(aes_key, "AES");

                        byte[] serverhash = CryptManager.func_75895_a(encReq.getServerID(), key, secretKey);
                        String s_serverhash = CryptManager.getHexString(serverhash);
                        //System.out.println("Server Hash " + s_serverhash);

                        byte[] sharedSecret = CryptManager.func_75894_a(key, secretKey.getEncoded());
                        byte[] verifyToken = CryptManager.func_75894_a(key, encReq.getVerifyToken());

                        if (session.isOnlineMode()) {
                            HTTPResponse http = HTTP.https("https://sessionserver.mojang.com/session/minecraft/join", "{\r\n"
                                    + "\"accessToken\": \"" + session.getAccessToken() + "\",\r\n"
                                    + "\"selectedProfile\": \"" + session.getProfileID() + "\",\r\n"
                                    + "\"serverId\": \"" + s_serverhash + "\"\r\n"
                                    + "}");
                            //System.out.println(http.getHeaders().get(null));
                            if (http == null || http.getHeaders().get(null).get(0).contains("403")) {
                                dis.close();
                                dos.close();
                                do {
                                    System.out.println("\rLogging in.. [FAIL]");
                                    System.err.println("Session invalid! Renewing");
                                    sleep(30000);
                                    System.out.print("Logging in.. [    ]");
                                } while (!session.renew());
                                break;
                            }
                        }
                        System.out.print("\rLogging in.. [### ]");
                        dos.writePacket(new Packet01EncryptionResponse(sharedSecret, verifyToken));
                        is = CryptManager.decryptInputStream(secretKey, sock.getInputStream());
                        os = CryptManager.encryptOuputStream(secretKey, sock.getOutputStream());
                        dis = new PacketInputStream(is, StreamState.PLAY);
                        dos = new PacketOutputStream(os, StreamState.PLAY);
                    } else if (op == 0) {
                        int slen = dis.readVarInt();
                        byte[] stuff = new byte[slen];
                        dis.readFully(stuff);
                        String reason = new String(stuff);
                        System.out.println("\rLogging in [FAIL]");
                        System.out.println("Disconneted: " + reason);
                        if (reason.contains("40")) {
                            session.renew();
                        }
                        sleep(30000);
                    } else if (op == 2) {
                        Packet02LoginSuccess p2 = new Packet02LoginSuccess();
                        p2.readPacket(dis, 0);
                        System.out.print("\rLogging in.. [####]");
                    } else if (op == 3) {
                        Packet03SetCompression p3 = new Packet03SetCompression();
                        p3.readPacket(dis, 0);
                        compressionEnabled = true;
                        //System.out.println("Set Compression Threshold " + p3.getThreshold());
                    } else if (op == 35) {
                        Packet23JoinGame p23 = new Packet23JoinGame();
                        p23.readPacket(dis, 0);
                        System.out.print("\rLogging in.. [ OK ]\r");
                        p23.process(context);
                        context.openConnection(is, os, compressionEnabled);
                        context.joinThreads();
                        break;
                    } else {
                        System.out.println("\rLogging in.. [FAIL]");
                        System.err.println("Protocol Error: Op " + op);
                        break;
                    }
                }
                context.closeConnection();
                sock.close();
                sleep(10000);
            } catch (IOException | InterruptedException ex) {
                System.err.println("Disconnected from " + serverIP + "! " + ex.getClass() + ": " + ex.getMessage());
            }
        }
    }

    public synchronized void shutdown() {
        keepAlive = false;
        interrupt();
        try {
            sock.close();
        } catch (IOException ex) {
        }
        context.closeConnection();
    }
}
