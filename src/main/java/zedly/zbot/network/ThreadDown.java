package zedly.zbot.network;

import java.util.LinkedList;
import zedly.zbot.network.packet.clientbound.ClientBoundPacket;
import zedly.zbot.GameSocket;

public class ThreadDown extends Thread {

    private final GameSocket gameSocket;
    private final LinkedList<ClientBoundPacket> receivedPackets = new LinkedList<>();

    public ThreadDown(GameSocket gameSocket) {
        this.gameSocket = gameSocket;
    }

    @Override
    public void run() {
        ClientBoundPacket newPacket;
        while (!isInterrupted()) {
            newPacket = gameSocket.readPacket();
            if (newPacket == null) {
                return;
            }
            sendToProcessor(newPacket);
        }
    }

    private void sendToProcessor(ClientBoundPacket p) {
        synchronized (receivedPackets) {
            receivedPackets.add(p);
            receivedPackets.notifyAll();
        }
    }
    
    public LinkedList<ClientBoundPacket> getQueue() {
        return receivedPackets;
    }

}
