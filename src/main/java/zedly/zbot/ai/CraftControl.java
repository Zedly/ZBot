package zedly.zbot.ai;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import zedly.zbot.BlockFace;
import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.StringUtil;
import zedly.zbot.entity.Entity;
import zedly.zbot.inventory.Inventory;
import zedly.zbot.network.packet.serverbound.Packet02ChatMessage;
import zedly.zbot.network.packet.serverbound.Packet03ClientStatus;
import zedly.zbot.network.packet.serverbound.Packet0AUseEntity;
import zedly.zbot.network.packet.serverbound.Packet12PlayerAbilities;
import zedly.zbot.network.packet.serverbound.Packet13PlayerDigging;
import zedly.zbot.network.packet.serverbound.Packet14EntityAction;
import zedly.zbot.network.packet.serverbound.Packet17HeldItemChange;
import zedly.zbot.network.packet.serverbound.Packet19UpdateSign;
import zedly.zbot.network.packet.serverbound.Packet1AAnimation;
import zedly.zbot.network.packet.serverbound.Packet1CPlayerBlockPlacement;
import zedly.zbot.network.packet.serverbound.Packet1DUseItem;
import zedly.zbot.util.Vector;

/**
 *
 * @author Dennis
 */
public class CraftControl implements Control, Runnable {

    private double stepResolution = 0.2;
    private final Object lock = "";
    private final GameContext context;
    private boolean valid = true;
    //private final CraftSelf self;

    @Override
    public void run() {
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    public CraftControl(GameContext context) {
        this.context = context;
    }

    public boolean moveTo(Location target) throws InterruptedException {
        checkValid();
        Location oldLoc = context.getSelf().getLocation();
        double stepDistance = target.distanceSquareTo(oldLoc);
        if (stepDistance > 25) {
            return false;
        }
        Vector direction = oldLoc.vectorTo(oldLoc).normalize();
        Location tempNewLoc;
        for (double distance = 0; distance < stepDistance; distance += stepResolution) {
            tempNewLoc = oldLoc.getRelative(direction.multiply(distance));
            context.getLocationUpdater().updatePosition(tempNewLoc);
            //self.moveTo(tempNewLoc);
            tick();
        }
        return true;
    }

    @Override
    public void breakBlock(int x, int y, int z) throws InterruptedException {
        breakBlock(x, y, z, 1000);
    }

    @Override
    public void breakBlock(int x, int y, int z, int millis) throws InterruptedException {
        checkValid();
        CallbackLock cLock = new CallbackLock();
        context.getUpThread().sendPacket(new Packet13PlayerDigging(0, x, y, z, 1));
        context.getMainThread().schedule(() -> {
            context.getUpThread().sendPacket(new Packet13PlayerDigging(2, x, y, z, 1));
            cLock.finish();
        }, millis, TimeUnit.MILLISECONDS);

        int i = 0;
        while (true) {
            tick();
            if (++i % 3 == 0) {
                swingArm(false);
            }
            if (cLock.isFinished()) {
                return;
            }
        }
    }

    @Override
    public void breakBlock(Location loc) throws InterruptedException {
        breakBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    @Override
    public void breakBlock(Location loc, int millis) throws InterruptedException {
        breakBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), millis);
    }

    @Override
    public void clickBlock(Location loc) throws InterruptedException {
        clickBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    @Override
    public void clickBlock(int x, int y, int z) throws InterruptedException {
        checkValid();
        context.getUpThread().sendPacket(new Packet13PlayerDigging(0, x, y, z, 0));
        context.getUpThread().sendPacket(new Packet13PlayerDigging(1, x, y, z, 0));
    }

    @Override
    public void placeBlock(int x, int y, int z, BlockFace bf) throws InterruptedException {
        checkValid();
        context.getUpThread().sendPacket(new Packet1CPlayerBlockPlacement(x, y, z, (byte) bf.numeric(), 0, (byte) 0, (byte) 0, (byte) 0));
    }

    @Override
    public void placeBlock(Location loc, BlockFace bf) throws InterruptedException {
        placeBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), bf);
    }

    @Override
    public void tick() throws InterruptedException {
        checkValid();
        synchronized (lock) {
            lock.wait();
        }
    }

    @Override
    public void tick(int ticks) throws InterruptedException {
        checkValid();
        for (int i = 0; i < ticks; i++) {
            tick();
        }
    }

    @Override
    public void interact(Entity ent) throws InterruptedException {
        checkValid();
        context.getUpThread().sendPacket(new Packet0AUseEntity(ent.getEntityId(), 0));
    }

    @Override
    public void attack(Entity ent) throws InterruptedException {
        checkValid();
        context.getUpThread().sendPacket(new Packet0AUseEntity(ent.getEntityId()));
    }

    @Override
    public void sneak(boolean sneak) throws InterruptedException {
        checkValid();
        context.getUpThread().sendPacket(new Packet14EntityAction(context.getSelf().getEntityId(), (sneak ? 0 : 1), 0));
        if (sneak) {
            stepResolution = 0.05;
        } else {
            stepResolution = 0.2;
        }
    }

    @Override
    public void sprint(boolean sprint) throws InterruptedException {
        checkValid();
        context.getUpThread().sendPacket(new Packet14EntityAction(context.getSelf().getEntityId(), (sprint ? 3 : 4), 0));
        if (sprint) {
            stepResolution = 0.3;
        } else {
            stepResolution = 0.2;
        }
    }

    @Override
    public void clickSlot(int slotId) throws InterruptedException {
        checkValid();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clickSlot(Inventory inv, int slotId) throws InterruptedException {
        checkValid();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeWindow() throws InterruptedException {
        checkValid();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void performAction(int action) throws InterruptedException {
        checkValid();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void respawn() throws InterruptedException {
        checkValid();
        context.getUpThread().sendPacket(new Packet03ClientStatus(0));
    }

    @Override
    public void selectSlot(int i) throws InterruptedException {
        checkValid();
        context.getSelf().getInventory().selectSlot(i);
        context.getUpThread().sendPacket(new Packet17HeldItemChange(i));
    }

    @Override
    public void sendChat(String message) throws InterruptedException {
        checkValid();
        ArrayList<String> lines = StringUtil.wrap(message, 100);
        for (String line : lines) {
            context.getUpThread().sendPacket(new Packet02ChatMessage(line));
        }
    }

    @Override
    public void setAbilities(int abilities) throws InterruptedException {
        checkValid();
        context.getUpThread().sendPacket(new Packet12PlayerAbilities((byte) abilities, 0, 0));
    }

    @Override
    public void swingArm(boolean leftHand) throws InterruptedException {
        checkValid();
        context.getUpThread().sendPacket(new Packet1AAnimation((leftHand ? 1 : 0)));
    }

    @Override
    public void useItem(boolean leftHand) throws InterruptedException {
        checkValid();
        context.getUpThread().sendPacket(new Packet1DUseItem((leftHand ? 1 : 0)));
    }

    @Override
    public void writeToSign(Location loc, String line1, String line2, String line3, String line4) throws InterruptedException {
        checkValid();
        context.getUpThread().sendPacket(new Packet19UpdateSign(loc, line1, line2, line3, line4));
    }

    public synchronized void invalidate() {
        valid = false;
    }

    public synchronized boolean isValid() {
        return valid;
    }

    private synchronized void checkValid() throws InterruptedException {
        if (!valid) {
            throw new InterruptedException("This Control has been invaldiated");
        }
    }

    private class CallbackLock {

        private boolean finished = false;

        public synchronized boolean isFinished() {
            return finished;
        }

        public synchronized void finish() {
            finished = true;
        }
    }
}
