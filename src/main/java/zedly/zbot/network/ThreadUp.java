package zedly.zbot.network;

import zedly.zbot.network.packet.serverbound.ServerBoundPacket;
import java.util.LinkedList;
import zedly.zbot.GameSocket;

public class ThreadUp extends Thread {

    private final GameSocket gameSocket;
    private final LinkedList<ServerBoundPacket> outgoingList = new LinkedList<>();
    private boolean running = true;

    public ThreadUp(GameSocket gameSocket) {
        this.gameSocket = gameSocket;
    }

    @Override
    public void run() {
        try {
            while (running()) {
                while (running() && hasPackets()) {
                    ServerBoundPacket p = getPacket();
                    gameSocket.writePacket(p);
                }
                synchronized (this) {
                    wait();
                }
            }
        } catch (InterruptedException ex) {
        }
    }

    public synchronized void sendPacket(ServerBoundPacket p) {
        outgoingList.add(p);
        notifyAll();
    }
    
    public synchronized void exit() {
        running = false;
        notifyAll();
    }

    private synchronized boolean running() {
        return running;
    }

    private synchronized boolean hasPackets() {
        return !outgoingList.isEmpty();
    }

    private synchronized ServerBoundPacket getPacket() {
        return outgoingList.remove();
    }
}
