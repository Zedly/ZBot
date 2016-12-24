/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.self;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import zedly.zbot.ClientSettings;
import zedly.zbot.GameContext;
import zedly.zbot.ServerConnection;
import zedly.zbot.StringUtil;
import zedly.zbot.event.Listener;
import zedly.zbot.inventory.CraftInventory;
import zedly.zbot.environment.CraftEnvironment;
import zedly.zbot.Location;
import zedly.zbot.entity.Entity;
import zedly.zbot.entity.CraftPlayer;
import zedly.zbot.environment.BlockFace;
import zedly.zbot.inventory.CraftPlayerInventory;
import zedly.zbot.network.ThreadLocationUpdater;
import zedly.zbot.network.packet.serverbound.Packet02ChatMessage;
import zedly.zbot.network.packet.serverbound.Packet03ClientStatus;
import zedly.zbot.network.packet.serverbound.Packet04ClientSettings;
import zedly.zbot.network.packet.serverbound.Packet0AUseEntity;
import zedly.zbot.network.packet.serverbound.Packet12PlayerAbilities;
import zedly.zbot.network.packet.serverbound.Packet13PlayerDigging;
import zedly.zbot.network.packet.serverbound.Packet14EntityAction;
import zedly.zbot.network.packet.serverbound.Packet17HeldItemChange;
import zedly.zbot.network.packet.serverbound.Packet19UpdateSign;
import zedly.zbot.network.packet.serverbound.Packet1AAnimation;
import zedly.zbot.network.packet.serverbound.Packet1CPlayerBlockPlacement;
import zedly.zbot.network.packet.serverbound.Packet1DUseItem;
import zedly.zbot.plugin.ZBotPlugin;

/**
 *
 * @author Dennis
 */
public class CraftSelf extends CraftPlayer implements Self {

    private final ServerConnection serverConnection;
    private final GameContext context;
    private CraftEnvironment environment;
    private CraftInventory inventory;
    private ClientSettings clientSettings;

    public CraftSelf(GameContext context, CraftEnvironment env, ThreadLocationUpdater locationUpdater, ClientSettings clientSettings) {
        this.context = context;
        this.environment = env;
        this.inventory = new CraftPlayerInventory(context);
        this.serverConnection = new ServerConnection(context.getServerIp(), context.getServerPort(), context.getSession().getActualUsername());
        this.clientSettings = clientSettings;
    }

    @Override
    public void attackEntity(Entity ent) {
        context.getUpThread().sendPacket(new Packet0AUseEntity(ent.getEntityId()));
    }

    @Override
    public void cancelTask(int i) {
        context.cancelTask(i);
    }

    @Override
    public void closeWindow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized ClientSettings getClientSettings() {
        return clientSettings;
    }

    @Override
    public synchronized void setClientSettings(ClientSettings clientSettings) {
        this.clientSettings = clientSettings;
        if (context.getSession().isOnlineMode()) {
            context.getUpThread().sendPacket(new Packet04ClientSettings(clientSettings));
        }
    }

    @Override
    public CraftEnvironment getEnvironment() {
        return environment;
    }

    @Override
    public synchronized CraftInventory getInventory() {
        return inventory;
    }

    @Override
    public Location getLocation() {
        return context.getLocationUpdater().getLocation();
    }

    @Override
    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    @Override
    public void interactWithEntity(Entity ent, boolean leftHand) {
        context.getUpThread().sendPacket(new Packet0AUseEntity(ent.getEntityId(), (leftHand ? 1 : 0)));
    }

    @Override
    public void interactWithEntity(Entity ent, Location loc, boolean leftHand) {
        context.getUpThread().sendPacket(new Packet0AUseEntity(ent.getEntityId(), loc, (leftHand ? 1 : 0)));
    }

    @Override
    public void lookAt(double yaw, double pitch) {
        Location l = getLocation();
        context.getLocationUpdater().updatePosition(new Location(l.getX(), l.getY(), l.getZ(), yaw, pitch));
    }

    @Override
    public void moveTo(double x, double y, double z) {
        Location l = getLocation();
        context.getLocationUpdater().updatePosition(new Location(x, y, z, l.getYaw(), l.getPitch()));
    }

    @Override
    public void moveTo(double x, double y, double z, double yaw, double pitch) {
        context.getLocationUpdater().updatePosition(new Location(x, y, z, yaw, pitch));
    }

    @Override
    public void moveTo(Location loc) {
        context.getLocationUpdater().updatePosition(loc);
    }

    @Override
    public void performAction(int action) {
        context.getUpThread().sendPacket(new Packet14EntityAction(getEntityId(), action, 0));
    }

    @Override
    public void breakBlock(int x, int y, int z) {
        context.getUpThread().sendPacket(new Packet13PlayerDigging(0, x, y, z, 0));
        context.getUpThread().sendPacket(new Packet13PlayerDigging(2, x, y, z, 0));
    }

    @Override
    public void breakBlock(int x, int y, int z, int millis, Runnable callback) {
        context.getUpThread().sendPacket(new Packet13PlayerDigging(0, x, y, z, 1));
        context.getMainThread().schedule(() -> {
            context.getUpThread().sendPacket(new Packet13PlayerDigging(2, x, y, z, 1));
            callback.run();
        }, millis, TimeUnit.MILLISECONDS);
    }

    @Override
    public void breakBlock(Location loc) {
        breakBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    @Override
    public void breakBlock(Location loc, int millis, Runnable callback) {
        breakBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), millis, callback);
    }

    @Override
    public void clickBlock(Location loc) {
        clickBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    @Override
    public void clickBlock(int x, int y, int z) {
        context.getUpThread().sendPacket(new Packet13PlayerDigging(0, x, y, z, 0));
        context.getUpThread().sendPacket(new Packet13PlayerDigging(1, x, y, z, 0));
    }

    @Override
    public void placeBlock(int x, int y, int z, BlockFace face) {
        int iFace = 255;
        if (face != null) {
            switch (face) {
                case DOWN:
                    iFace = 0;
                    break;
                case UP:
                    iFace = 1;
                    break;
                case NORTH:
                    iFace = 2;
                    break;
                case SOUTH:
                    iFace = 3;
                    break;
                case WEST:
                    iFace = 4;
                    break;
                case EAST:
                    iFace = 5;
                    break;
            }
        }
        context.getUpThread().sendPacket(new Packet1CPlayerBlockPlacement(x, y, z, (byte) iFace, 0, (byte) 0, (byte) 0, (byte) 0));
    }

    @Override
    public void placeBlock(Location loc, BlockFace face) {
        placeBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), face);
    }

    @Override
    public void registerEvents(Listener listener) {
        context.getEventDispatcher().addPermanentHandler(listener);
    }

    @Override
    public void respawn() {
        context.getUpThread().sendPacket(new Packet03ClientStatus(0));
    }

    @Override
    public int scheduleSyncDelayedTask(ZBotPlugin plugin, Runnable r, long delay) {
        return context.scheduleSyncDelayedTask(plugin, r, delay);
    }

    @Override
    public int scheduleSyncRepeatingTask(ZBotPlugin plugin, Runnable r, long delay, long interval) {
        return context.scheduleSyncRepeatingTask(plugin, r, delay, interval);
    }

    @Override
    public int scheduleSyncRepeatingTask(ZBotPlugin plugin, Runnable r, long interval) {
        return context.scheduleSyncRepeatingTask(plugin, r, interval, interval);
    }

    @Override
    public void selectSlot(int i) {
        inventory.selectSlot(i);
        context.getUpThread().sendPacket(new Packet17HeldItemChange(i));
    }

    @Override
    public void sendChat(String message) {
        ArrayList<String> lines = StringUtil.wrap(message, 240);
        for (String line : lines) {
            context.getUpThread().sendPacket(new Packet02ChatMessage(line));
        }
    }

    @Override
    public void setAbilities(int abilities) {
        context.getUpThread().sendPacket(new Packet12PlayerAbilities((byte) abilities, 0, 0));
    }

    @Override
    public void shutdown() {
        context.closeConnection();
        context.finish();
        System.exit(0);
    }

    @Override
    public void sneak(boolean sneak) {
        context.getUpThread().sendPacket(new Packet14EntityAction(entityId, (sneak ? 0 : 1), 0));
    }

    @Override
    public void sprint(boolean sprint) {
        context.getUpThread().sendPacket(new Packet14EntityAction(entityId, (sprint ? 3 : 4), 0));
    }

    @Override
    public void swingArm(boolean leftHand) {
        context.getUpThread().sendPacket(new Packet1AAnimation((leftHand ? 1 : 0)));
    }

    @Override
    public void useItem(boolean leftHand) {
        context.getUpThread().sendPacket(new Packet1DUseItem((leftHand ? 1 : 0)));
    }

    @Override
    public void unregisterEvents(Listener listener) {
        context.getEventDispatcher().removePermanentHandler(listener);
    }

    @Override
    public void writeToSign(Location loc, String line1, String line2, String line3, String line4) {
        context.getUpThread().sendPacket(new Packet19UpdateSign(loc, line1, line2, line3, line4));
    }

    @Override
    public int getScore() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getSkinFlags() {
        return clientSettings.getSkinFlags();
    }

    @Override
    public boolean isLeftHanded() {
        return clientSettings.isLeftHanded();
    }

    /**
     * @param externalInventory the externalInventory to set
     */
    public void setInventory(CraftInventory inventory) {
        this.inventory.close();
        this.inventory = inventory;
    }

}
