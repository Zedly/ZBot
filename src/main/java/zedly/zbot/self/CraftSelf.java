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
import zedly.zbot.event.Event;
import zedly.zbot.inventory.CraftChestInventory;
import zedly.zbot.inventory.CraftCraftingTableInventory;
import zedly.zbot.inventory.CraftEnchantingTableInventory;
import zedly.zbot.inventory.CraftFurnaceInventory;
import zedly.zbot.inventory.CraftPlayerInventory;
import zedly.zbot.inventory.CraftVillagerInventory;
import zedly.zbot.network.ThreadLocationUpdater;
import zedly.zbot.network.mappings.InventoryType;
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
    private int foodLevel = 0;
    private double saturation = 0;
    private int xpLevels = 0;
    private double xpPercent = 0;
    private int blockInteractionSequenceNr = 0;
    
    public CraftSelf(GameContext context, CraftEnvironment env, ThreadLocationUpdater locationUpdater, ClientSettings clientSettings) {
        this.context = context;
        this.environment = env;
        this.inventory = new CraftPlayerInventory(context);
        this.serverConnection = new ServerConnection(context.getServerIp(), context.getServerPort(), context.getSession().getActualUsername());
        this.clientSettings = clientSettings;
    }

    @Override
    public void attackEntity(Entity ent) {
        context.getUpThread().sendPacket(new Packet10Interact(ent.getEntityId()));
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
            context.getUpThread().sendPacket(new Packet0CCloseContainer(inventory.windowId()));
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
            context.getUpThread().sendPacket(new Packet08ClientInformation(clientSettings));
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
        context.getUpThread().sendPacket(new Packet10Interact(ent.getEntityId(), (leftHand ? 1 : 0)));
    }

    @Override
    public void interactWithEntity(Entity ent, Location loc, boolean leftHand) {
        context.getUpThread().sendPacket(new Packet10Interact(ent.getEntityId(), loc, (leftHand ? 1 : 0)));
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

    //@Override
    //public void performAction(int action) {
    //    context.getUpThread().sendPacket(new Packet1DPlayerAction(getEntityId(), action, 0));
    //}

    @Override
    public int breakBlock(int x, int y, int z) {
        return breakBlock(new Location(x, y, z));
    }

    @Override
    public int breakBlock(int x, int y, int z, int millis, Runnable callback) {
        return breakBlock(new Location(x, y, z), millis, callback);
    }

    @Override
    public int breakBlock(Location loc) {
        context.getUpThread().sendPacket(new Packet1DPlayerAction(Packet1DPlayerAction.Action.START_DIGGING, loc, 0, blockInteractionSequenceNr));
        context.getUpThread().sendPacket(new Packet1DPlayerAction(Packet1DPlayerAction.Action.FINISH_DIGGING, loc, 0, blockInteractionSequenceNr));
        return blockInteractionSequenceNr++;
    }

    @Override
    public int breakBlock(Location loc, int millis, Runnable callback) {
        final int seq = blockInteractionSequenceNr;
        context.getUpThread().sendPacket(new Packet1DPlayerAction(Packet1DPlayerAction.Action.START_DIGGING, loc, 1, seq));
        context.getMainThread().schedule(() -> {
            context.getUpThread().sendPacket(new Packet1DPlayerAction(Packet1DPlayerAction.Action.FINISH_DIGGING, loc, 1, seq));
            callback.run();
        }, millis, TimeUnit.MILLISECONDS);
        blockInteractionSequenceNr++;
        return seq;
    }

    @Override
    public int clickBlock(Location loc) {
        context.getUpThread().sendPacket(new Packet1DPlayerAction(Packet1DPlayerAction.Action.START_DIGGING, loc, 0, blockInteractionSequenceNr));
        context.getUpThread().sendPacket(new Packet1DPlayerAction(Packet1DPlayerAction.Action.CANCEL_DIGGING, loc, 0, blockInteractionSequenceNr));
        return blockInteractionSequenceNr++;
    }

    @Override
    public int clickBlock(int x, int y, int z) {
        return clickBlock(new Location(x, y, z));
    }

    @Override
    public int placeBlock(int x, int y, int z, BlockFace face) {
        return placeBlock(new Location(x, y, z), face);
    }

    @Override
    public int placeBlock(Location loc, BlockFace face) {
        return placeBlock(loc, face, 0);
    }

    public int placeBlock(Location loc, BlockFace face, int usedHand) {
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
        context.getUpThread().sendPacket(new Packet31UseItemOn(usedHand, loc, (byte) iFace, 0.5, 0.5, 0.5, false, blockInteractionSequenceNr));
        return blockInteractionSequenceNr++;
    }

    @Override
    public int eatHeldItem() {
        return eatHeldItem(0);
    }

    @Override
    public int eatHeldItem(int usedHand) {
        return eatHeldItem(usedHand, () -> {
        });
    }

    @Override
    public int eatHeldItem(int usedHand, Runnable callback) {
        final int seq = blockInteractionSequenceNr;
        context.getUpThread().sendPacket(new Packet32UseItem(usedHand, seq));
        context.getMainThread().schedule(() -> {
            context.getUpThread().sendPacket(new Packet1DPlayerAction(5, new Location(0, 0, 0), 0, seq));
            callback.run();
        }, 2000, TimeUnit.MILLISECONDS);
        return blockInteractionSequenceNr++;
    }

    @Override
    public void recipeBookStatus(int bookType, boolean open, boolean filterActive) {
        context.getUpThread().sendPacket(new Packet21ChangeRecipeBookSettings(bookType, open, filterActive));
    }

    @Override
    public void registerEvents(Listener listener) {
        context.getEventDispatcher().addPermanentHandler(listener);
    }

    @Override
    public void requestRecipe(String recipeId, boolean makeAll) {
        context.getUpThread().sendPacket(new Packet1BPlaceRecipe(getInventory().windowId(), recipeId, makeAll));
    }

    @Override
    public void respawn() {
        context.getUpThread().sendPacket(new Packet07ClientCommand(0));
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
        context.getUpThread().sendPacket(new Packet28SetHeldItem(i));
    }

    @Override
    public void sendChat(String message) {
        ArrayList<String> lines = StringUtil.wrap(message, 240);
        for (String line : lines) {
            context.getUpThread().sendPacket(new Packet05ChatMessage(line));
        }
    }

    @Override
    public void setAbilities(int abilities) {
        context.getUpThread().sendPacket(new Packet1CPlayerAbilities((byte) abilities));
    }

    @Override
    public Event setStatus(int status) {
        stopEating();
        return super.setStatus(status);
    }
    
    @Override
    public int stopEating() {
        context.getUpThread().sendPacket(new Packet1DPlayerAction(5, new Location(0, 0, 0), 0, blockInteractionSequenceNr));
        return blockInteractionSequenceNr++;
    }
    
    @Override
    public void shutdown() {
        context.closeConnection();
        context.finish();
        System.exit(0);
    }

    @Override
    public void sneak(boolean sneak) {
        context.getUpThread().sendPacket(new Packet1EPlayerCommand(entityId, (sneak ? 0 : 1), 0));
    }

    @Override
    public void sprint(boolean sprint) {
        context.getUpThread().sendPacket(new Packet1EPlayerCommand(entityId, (sprint ? 3 : 4), 0));
    }

    @Override
    public void swingArm(boolean offHand) {
        context.getUpThread().sendPacket(new Packet2FSwingArm(offHand ? 1 : 0));
    }

    @Override
    public int useItem(boolean leftHand) {
        context.getUpThread().sendPacket(new Packet32UseItem((leftHand ? 1 : 0), blockInteractionSequenceNr));
        return blockInteractionSequenceNr++;
    }

    @Override
    public void unregisterEvents(Listener listener) {
        context.getEventDispatcher().removePermanentHandler(listener);
    }

    @Override
    public void writeToSign(Location loc, String line1, String line2, String line3, String line4) {
        context.getUpThread().sendPacket(new Packet2EUpdateSign(loc, line1, line2, line3, line4));
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

    /**
     * @return the foodlevel
     */
    public int getFoodLevel() {
        return foodLevel;
    }

    /**
     * @param foodlevel the foodlevel to set
     */
    public void setFoodLevel(int foodlevel) {
        this.foodLevel = foodlevel;
    }

    /**
     * @return the saturation
     */
    public double getSaturation() {
        return saturation;
    }

    /**
     * @param saturation the saturation to set
     */
    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    public void setHealth(double health) {
        this.health = (float) health;
    }
}
