package zedly.zbot.network;

import zedly.zbot.HTTP;
import zedly.zbot.network.packet.clientbound.Packet00Disconnect;
import zedly.zbot.network.packet.serverbound.Packet00LoginStart;
import zedly.zbot.network.packet.clientbound.Packet01EncryptionRequest;
import zedly.zbot.network.packet.serverbound.Packet01EncryptionResponse;
import zedly.zbot.network.packet.clientbound.Packet02LoginSuccess;
import zedly.zbot.network.packet.clientbound.Packet03SetCompression;
import zedly.zbot.Session;
import zedly.zbot.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.PublicKey;
import java.util.zip.DataFormatException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import zedly.zbot.GameContext;
import zedly.zbot.HTTP.HTTPResponse;
import zedly.zbot.network.packet.clientbound.ClientBoundPacket;
import zedly.zbot.network.packet.clientbound.Packet28Join;
import zedly.zbot.network.packet.serverbound.Packet00Handshake;
import zedly.zbot.network.packet.serverbound.Packet00LoginStart;

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
                System.out.print("\rLogging in.. [#   ]");
                sock = new Socket();
                sock.setSoTimeout(60000);
                sock.connect(new InetSocketAddress(InetAddress.getByName(serverIP), serverPort));

                OutputStream os = sock.getOutputStream();
                InputStream is = sock.getInputStream();
                PacketInputStream dis = new PacketInputStream(is, StreamState.LOGIN);
                PacketOutputStream dos = new PacketOutputStream(os, StreamState.LOGIN);

                Packet00Handshake pack = new Packet00Handshake(758, serverIP, serverPort, 2);
                Packet00LoginStart loginStart = new Packet00LoginStart(session.getActualUsername());

                dos.writePacket(pack);
                dos.writePacket(loginStart);

                while (true) {
                    try {
                        ClientBoundPacket p = dis.readPacket();
                        if (p instanceof Packet01EncryptionRequest) {
                            System.out.println("\rExchanging keys...\n");
                            Packet01EncryptionRequest encReq = (Packet01EncryptionRequest) p;
                            System.out.println("Server Public Key " + Util.bytesToHex((encReq.getPublicKey())));
                            PublicKey publicKey = CryptManager.decodePublicKey(encReq.getPublicKey());
                            byte[] secretKeyBytes = "icetubeisafoggot".getBytes();
                            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "AES");

                            byte[] serverhash = CryptManager.calculateServerHash(encReq.getServerID(), publicKey, secretKey);
                            String s_serverhash = CryptManager.getHexString(serverhash);
                            System.out.println("Server Hash " + s_serverhash);

                            byte[] sharedSecret = CryptManager.encryptWithPublicKey(publicKey, secretKey.getEncoded());
                            byte[] verifyToken = CryptManager.encryptWithPublicKey(publicKey, encReq.getVerifyToken());

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
                            dis = new PacketInputStream(is, StreamState.LOGIN);
                            dos = new PacketOutputStream(os, StreamState.LOGIN);
                        } else if (p instanceof Packet00Disconnect) {
                            Packet00Disconnect p00 = (Packet00Disconnect) p;
                            String reason = p00.getFormattedReason();
                            System.out.println("\rLogging in [FAIL]");
                            if (reason.contains("40")) {
                                session.renew();
                            }
                            sleep(30000);
                        } else if (p instanceof Packet02LoginSuccess) {
                            System.out.print("\rLogging in.. [####]");
                        } else if (p instanceof Packet28Join) {
                            Packet28Join p28 = (Packet28Join) p;
                            System.out.print("\rLogging in.. [ OK ]\r");
                            p28.process(context);
                            context.openConnection(dis, dos);
                            context.joinThreads();
                            break;
                        }
                    } catch (IllegalArgumentException | DataFormatException ex) {
                        ex.printStackTrace();
                        dis.printCrashInfo();
                        break;
                    }
                }
                context.closeConnection();
                sock.close();
                sleep(10000);
            } catch (Exception ex) {
                System.err.println("Disconnected from " + serverIP + "! " + ex.getClass() + ": " + ex.getMessage());
                ex.printStackTrace();
                try {
                    sleep(10000);
                } catch (InterruptedException ex2) {
                }
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
