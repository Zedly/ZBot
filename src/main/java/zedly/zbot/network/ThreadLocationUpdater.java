package zedly.zbot.network;

import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.network.packet.serverbound.Packet15SetPlayerPositionandRotation;
import zedly.zbot.network.packet.serverbound.Packet17SetPlayerOnGround;

/**
 *
 * @author Dennis
 */
public class ThreadLocationUpdater extends Thread {

    private ThreadUp upThread;
    private GameContext context;
    private boolean updated = false;
    private Location location;

    public ThreadLocationUpdater(GameContext context) {
        this.context = context;
    }

    public void run() {
        upThread = context.getUpThread();
        try {
            while (!isInterrupted()) {
                if (updated) {
                    upThread.sendPacket(new Packet15SetPlayerPositionandRotation(location, true));
                } else {
                    upThread.sendPacket(new Packet17SetPlayerOnGround(true));
                }
                updated = false;
                synchronized (this) {
                    wait(200);
                }
            }
        } catch (InterruptedException ex) {
        }
    }

    public synchronized void updatePosition(Location loc) {
        location = loc;
        updated = true;
        if (isAlive()) {
            notify();
        } else {
            start();
        }
    }

    public synchronized Location getLocation() {
        return location;
    }
}
