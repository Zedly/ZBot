package zedly.zbot.api.self;

import zedly.zbot.ClientSettings;
import zedly.zbot.Location;
import zedly.zbot.ServerConnection;
import zedly.zbot.api.entity.Entity;
import zedly.zbot.api.entity.Player;
import zedly.zbot.api.environment.Environment;
import zedly.zbot.api.event.Listener;
import zedly.zbot.api.inventory.Inventory;
import zedly.zbot.plugin.ZBotPlugin;

public interface Self extends Player {

    void attackEntity(Entity ent);
    
    void cancelTask(int i);
    
    void closeWindow();

    ClientSettings getClientSettings();

    int getEntityId();

    Environment getEnvironment();

    Inventory getInventory();

    ServerConnection getServerConnection();

    Location getLocation();
    
    void interactWithEntity(Entity ent, boolean leftHand);
    
    void interactWithEntity(Entity ent, Location loc, boolean leftHand);

    void moveTo(double x, double y, double z);

    void moveTo(double x, double y, double z, double yaw, double pitch);

    void moveTo(Location loc);

    void performAction(int action);

    void placeBlock(int x, int y, int z);

    void registerEvents(Listener listener);

    void respawn();

    int scheduleSyncDelayedTask(ZBotPlugin plugin, Runnable r, long delay);

    int scheduleSyncRepeatingTask(ZBotPlugin plugin, Runnable r, long delay, long interval);

    void selectSlot(int i);

    void sendChat(String message);

    void setAbilities(int abilities);

    void setClientSettings(ClientSettings clientSettings);

    void shutdown();

    void sneak(boolean sneak);
    
    void sprint(boolean sprint);
    
    void swingArm(boolean leftHand);
    
    void unregisterEvents(Listener listener);
    
    void writeToSign(Location loc, String line1, String line2, String line3, String line4);

}
