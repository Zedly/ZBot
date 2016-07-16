package zedly.zbot.network;

import zedly.zbot.network.packet.clientbound.ClientBoundPacket;
import zedly.zbot.Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import zedly.zbot.GameContext;
import zedly.zbot.ZBotThreadPoolExecutor;
import zedly.zbot.network.packet.clientbound.Packet03SpawnMob;
import zedly.zbot.network.packet.clientbound.Packet39EntityMetadata;

public class ThreadDown extends Thread {

    private final ExtendedDataInputStream dis;
    private final ZBotThreadPoolExecutor mainThread;
    private final Inflater inflater;
    private final GameContext context;
    private ExtendedDataInputStream bis;
    private ByteArrayInputStream bbis;
    private final boolean compressionEnabled;
    private int[] lastOps;
    private int lastOpPtr = 0;

    public ThreadDown(InputStream is, GameContext context, boolean compressionEnabled) {
        this.lastOps = new int[16];
        dis = new ExtendedDataInputStream(is);
        this.context = context;
        this.mainThread = context.getMainThread();
        this.compressionEnabled = compressionEnabled;
        inflater = new Inflater();
    }

    @Override
    public void run() {
        //bis = new ByteArrayInputStream();
        int op = 0;
        int clen;
        ClientBoundPacket p;
        try {
            while (!isInterrupted()) {
                int len = dis.readVarInt();
                //System.out.println("Play Debug: Len " + len);
                if (compressionEnabled) {
                    clen = dis.readVarInt();
                    //System.out.println("Play Debug: CLen " + clen);
                    if (clen != 0) {
                        //System.out.println("Compressed Debug: Len " + len + " clen " + clen);
                        byte[] cdata = new byte[len - Util.getVarIntLength(clen)];
                        byte[] data = new byte[clen];
                        dis.readFully(cdata);
                        inflater.setInput(cdata);
                        inflater.inflate(data);
                        inflater.reset();
                        bbis = new ByteArrayInputStream(data);
                        bis = new ExtendedDataInputStream(bbis);

                        op = bis.readVarInt();
                        lastOps[(lastOpPtr++) % 16] = op;
                        //System.out.println("Play Debug: Op " + Integer.toHexString(op));
                        p = Packet.getInstanceClientBound(op);
                        p.readPacket(bis, clen - 1);
                        if (bbis.available() != 0) {
                            System.err.println("Dropped " + bbis.available() + " bytes in compressed packet " + Integer.toHexString(op) + " of size " + clen + "!");
                        }
                    } else {
                        op = dis.readVarInt();
                        lastOps[(lastOpPtr++) % 16] = op;
                        //System.out.println("Play Debug: Op " + Integer.toHexString(op));
                        p = Packet.getInstanceClientBound(op);
                        p.readPacket(dis, len - 2);
                    }
                } else {
                    op = dis.readVarInt();
                    lastOps[(lastOpPtr++) % 16] = op;
                    //System.out.println("Play Debug: Op " + Integer.toHexString(op));
                    p = Packet.getInstanceClientBound(op);
                    p.readPacket(dis, len - 1);
                }
                mainThread.processPacket(p);
            }
        } catch (IOException ex) {
            System.err.println("Connection lost! Reconnecting");
            ex.printStackTrace();
        } catch (InstantiationException | IllegalAccessException | DataFormatException ex) {
            Logger.getLogger(ThreadDown.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            System.err.println("Unknown Packet ID: " + Integer.toHexString(op));
            ex.printStackTrace();
        } finally {
            System.out.println("Crash Info:");
            System.out.println("Died after " + lastOpPtr + " packets");
            System.out.print("Last received Packets: ");
            for (int i = lastOpPtr; i < lastOpPtr + 16; i++) {
                System.out.print("0x" + Integer.toHexString(lastOps[i % 16]) + " ");
            }
            System.out.print("\r\n");
        }
    }
}
