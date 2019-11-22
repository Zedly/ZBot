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
import zedly.zbot.BlockFace;
import zedly.zbot.inventory.CraftChestInventory;
import zedly.zbot.inventory.CraftCraftingTableInventory;
import zedly.zbot.inventory.CraftEnchantingTableInventory;
import zedly.zbot.inventory.CraftFurnaceInventory;
import zedly.zbot.inventory.CraftPlayerInventory;
import zedly.zbot.inventory.CraftVillagerInventory;
import zedly.zbot.network.ThreadLocationUpdater;
import zedly.zbot.network.mappings.InventoryType;
import zedly.zbot.network.packet.serverbound.Packet03ChatMessage;
import zedly.zbot.network.packet.serverbound.Packet04ClientStatus;
import zedly.zbot.network.packet.serverbound.Packet05ClientSettings;
import zedly.zbot.network.packet.serverbound.Packet0EUseEntity;
import zedly.zbot.network.packet.serverbound.*;
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
    private int xpLevels = 0;
    private double xpPercent = 0;

    public CraftSelf(GameContext context, CraftEnvironment env, ThreadLocationUpdater locationUpdater, ClientSettings clientSettings) {
        this.context = context;
        this.environment = env;
        this.inventory = new CraftPlayerInventory(context);
        this.serverConnection = new ServerConnection(context.getServerIp(), context.getServerPort(), context.getSession().getActualUsername());
        this.clientSettings = clientSettings;
    }

    @Override
    public void attackEntity(Entity ent) {
        context.getUpThread().sendPacket(new Packet0EUseEntity(ent.getEntityId()));
        swingArm(false);
    }

    @Override
    public void cancelTask(int i) {
        context.cancelTask(i);
    }

    @Override
    public void closeWindow() {
        closeWindow(true);
    }

    public void closeWindow(boolean sendPacket) {
        if (sendPacket) {
            context.getUpThread().sendPacket(new Packet0ACloseWindow(inventory.windowId()));
        }
        CraftInventory newInventory = new CraftPlayerInventory(context);
        for (int i = 0; i < 36; i++) {
            newInventory.setSlot(newInventory.getStaticOffset() + i, inventory.getSlot(inventory.getStaticOffset() + i));
        }
        inventory.close();
        inventory = newInventory;
    }

    @Override
    public synchronized ClientSettings getClientSettings() {
        return clientSettings;
    }

    @Override
    public synchronized void setClientSettings(ClientSettings clientSettings) {
        this.clientSettings = clientSettings;
        if (context.getSession().isOnlineMode()) {
            context.getUpThread().sendPacket(new Packet05ClientSettings(clientSettings));
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
    public String getName() {
        return context.getSession().getActualUsername();
    }

    @Override
    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    @Override
    public void interactWithEntity(Entity ent, boolean leftHand) {
        context.getUpThread().sendPacket(new Packet0EUseEntity(ent.getEntityId(), (leftHand ? 1 : 0)));
    }

    @Override
    public void interactWithEntity(Entity ent, Location loc, boolean leftHand) {
        context.getUpThread().sendPacket(new Packet0EUseEntity(ent.getEntityId(), loc, (leftHand ? 1 : 0)));
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
        context.getUpThread().sendPacket(new Packet1BEntityAction(getEntityId(), action, 0));
    }

    @Override
    public void breakBlock(int x, int y, int z) {
        breakBlock(new Location(x, y, z));
    }

    @Override
    public void breakBlock(int x, int y, int z, int millis, Runnable callback) {
        breakBlock(new Location(x, y, z), millis, callback);
    }

    @Override
    public void breakBlock(Location loc) {
        context.getUpThread().sendPacket(new Packet1APlayerDigging(0, loc, 0));
        context.getUpThread().sendPacket(new Packet1APlayerDigging(2, loc, 0));
    }

    @Override
    public void breakBlock(Location loc, int millis, Runnable callback) {
        context.getUpThread().sendPacket(new Packet1APlayerDigging(0, loc, 1));
        context.getMainThread().schedule(() -> {
            context.getUpThread().sendPacket(new Packet1APlayerDigging(2, loc, 1));
            callback.run();
        }, millis, TimeUnit.MILLISECONDS);
    }

    @Override
    public void clickBlock(Location loc) {
        context.getUpThread().sendPacket(new Packet1APlayerDigging(0, loc, 0));
        context.getUpThread().sendPacket(new Packet1APlayerDigging(1, loc, 0));
    }

    @Override
    public void clickBlock(int x, int y, int z) {
        clickBlock(new Location(x, y, z));
    }

    @Override
    public void placeBlock(int x, int y, int z, BlockFace face) {
        placeBlock(new Location(x, y, z), face);
    }

    @Override
    public void placeBlock(Location loc, BlockFace face) {
        placeBlock(loc, face, 0);
    }

    public void placeBlock(Location loc, BlockFace face, int usedHand) {
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
        context.getUpThread().sendPacket(new Packet2CPlayerBlockPlacement(usedHand, loc, (byte) iFace, 0.5, 0.5, 0.5, false));
    }

    @Override
    public void registerEvents(Listener listener) {
        context.getEventDispatcher().addPermanentHandler(listener);
    }

    @Override
    public void respawn() {
        context.getUpThread().sendPacket(new Packet04ClientStatus(0));
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
        context.getUpThread().sendPacket(new Packet23HeldItemChange(i));
    }

    @Override
    public void sendChat(String message) {
        ArrayList<String> lines = StringUtil.wrap(message, 240);
        for (String line : lines) {
            context.getUpThread().sendPacket(new Packet03ChatMessage(line));
        }
    }

    @Override
    public void setAbilities(int abilities) {
        context.getUpThread().sendPacket(new Packet19PlayerAbilities((byte) abilities, 0, 0));
    }

    @Override
    public void shutdown() {
        context.closeConnection();
        context.finish();
        System.exit(0);
    }

    @Override
    public void sneak(boolean sneak) {
        context.getUpThread().sendPacket(new Packet1BEntityAction(entityId, (sneak ? 0 : 1), 0));
    }

    @Override
    public void sprint(boolean sprint) {
        context.getUpThread().sendPacket(new Packet1BEntityAction(entityId, (sprint ? 3 : 4), 0));
    }

    @Override
    public void swingArm(boolean offHand) {
        context.getUpThread().sendPacket(new Packet2AAnimation(offHand ? 1 : 0));
    }

    @Override
    public void useItem(boolean leftHand) {
        context.getUpThread().sendPacket(new Packet2DUseItem((leftHand ? 1 : 0)));
    }

    @Override
    public void unregisterEvents(Listener listener) {
        context.getEventDispatcher().removePermanentHandler(listener);
    }

    @Override
    public void writeToSign(Location loc, String line1, String line2, String line3, String line4) {
        context.getUpThread().sendPacket(new Packet29UpdateSign(loc, line1, line2, line3, line4));
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

    public void openWindow(InventoryType type, int id, String title) {

        CraftInventory inv = null;
        switch (type) {
            case MINECRAFT_BLAST_FURNACE:
            case MINECRAFT_FURNACE:
            case MINECRAFT_SMOKER:
                inv = new CraftFurnaceInventory(context, id, title);
                break;
            case MINECRAFT_ENCHANTMENT:
                inv = new CraftEnchantingTableInventory(context, id, title);
                break;
            case MINECRAFT_GENERIC_9X1:
                inv = new CraftChestInventory(context, id, 9, title);
                break;
            case MINECRAFT_GENERIC_9X2:
                inv = new CraftChestInventory(context, id, 18, title);
                break;
            case MINECRAFT_GENERIC_9X3:
            case MINECRAFT_SHULKER_BOX:
                inv = new CraftChestInventory(context, id, 27, title);
                break;
            case MINECRAFT_GENERIC_9X4:
                inv = new CraftChestInventory(context, id, 36, title);
                break;
            case MINECRAFT_GENERIC_9X5:
                inv = new CraftChestInventory(context, id, 45, title);
                break;
            case MINECRAFT_GENERIC_9X6:
                inv = new CraftChestInventory(context, id, 54, title);
                break;
            case MINECRAFT_GENERIC_3X3:
                inv = new CraftChestInventory(context, id, 9, title);
                break;
            case MINECRAFT_ANVIL:
                inv = new CraftChestInventory(context, id, 3, title);
                break;
            case MINECRAFT_BEACON:
                inv = new CraftChestInventory(context, id, 1, title);
                break;
            case MINECRAFT_BREWING_STAND:
                inv = new CraftChestInventory(context, id, 5, title);
                break;
            case MINECRAFT_CRAFTING:
                inv = new CraftCraftingTableInventory(context, id, title);
                break;
            case MINECRAFT_GRINDSTONE:
                inv = new CraftChestInventory(context, id, 3, title);
                break;
            case MINECRAFT_HOPPER:
                inv = new CraftChestInventory(context, id, 5, title);
                break;
            case MINECRAFT_LECTERN:
                inv = new CraftChestInventory(context, id, 1, title);
                break;
            case MINECRAFT_LOOM:
                inv = new CraftChestInventory(context, id, 4, title);
                break;
            case MINECRAFT_MERCHANT:
                inv = new CraftVillagerInventory(context, id, title);
                break;
            case MINECRAFT_CARTOGRAPHY:
                inv = new CraftChestInventory(context, id, 3, title);
                break;
            case MINECRAFT_STONECUTTER:
                inv = new CraftChestInventory(context, id, 2, title);
                break;

        }
        if (inv == null) {
            System.err.println("Opened unknown inventory: " + type);
        } else {
            setInventory(inv);
        }
    }

    /**
     * @param inventory the Inventory to set
     */
    public void setInventory(CraftInventory inventory) {
        this.inventory.close();
        this.inventory = inventory;
    }

    public void setExperience(int levels, double percent) {
        this.xpLevels = levels;
        this.xpPercent = percent;
    }

    @Override
    public int getXPLevels() {
        return xpLevels;
    }

    @Override
    public double getXPPercent() {
        return xpPercent;
    }

}
