package zedly.zbot.network;

import zedly.zbot.network.packet.clientbound.ClientBoundPacket;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import zedly.zbot.GameContext;
import zedly.zbot.ZBotThreadPoolExecutor;

public class ThreadDown extends Thread {

    private final PacketInputStream dis;
    private final ZBotThreadPoolExecutor mainThread;

    public ThreadDown(PacketInputStream is, GameContext context) {
        dis = is;
        this.mainThread = context.getMainThread();
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                ClientBoundPacket p = dis.readPacket();
                mainThread.processPacket(p);
            }
        } catch (IOException ex) {
            System.err.println("Connection lost! Reconnecting");
            ex.printStackTrace();
        } catch (InstantiationException | IllegalAccessException | DataFormatException ex) {
            Logger.getLogger(ThreadDown.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            int[] lastOps = dis.getLastOps();
            int lastOpPtr = dis.getNumPacketsReceived();
            dis.printCrashInfo();
        }
    }
}
