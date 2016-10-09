package zedly.zbot.network;

import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.network.packet.serverbound.Packet0FPlayer;
import zedly.zbot.network.packet.serverbound.Packet0DPlayerPositionAndLook;

/**
 *
 * @author Dennis
 */
public class ThreadLocationUpdater extends Thread {

    private ThreadUp upThread;
    private GameContext context;
    private boolean updated = false;
    private Location location;
    private int noMovementTimer = 20;
    private boolean alive = true;

    public ThreadLocationUpdater(GameContext context) {
        this.context = context;
    }

    public void run() {
        upThread = context.getUpThread();
        try {
            while (!isInterrupted() && getAlive()) {
                if (updated || --noMovementTimer == 0) {
                    upThread.sendPacket(new Packet0DPlayerPositionAndLook(location));
                    noMovementTimer = 20;
                } else {
                    upThread.sendPacket(new Packet0FPlayer(true));
                }
                updated = false;
                synchronized (this) {
                    wait(50);
                }
            }
        } catch (InterruptedException ex) {
        }
    }

    private synchronized boolean getAlive() {
        return alive;
    }

    public synchronized void exit() {
        alive = false;
        notifyAll();
    }

    public synchronized void updatePosition(Location loc) {
        location = loc;
        updated = true;
        if (isAlive()) {
            notifyAll();
        } else {
            start();
        }
    }

    public synchronized Location getLocation() {
        return location;
    }
}
