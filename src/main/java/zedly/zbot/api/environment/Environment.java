package zedly.zbot.api.environment;

import java.util.Collection;
import zedly.zbot.api.entity.Entity;

import java.util.UUID;
import zedly.zbot.Location;
import zedly.zbot.api.block.Block;

public interface Environment {

    Collection<Entity> getEntities();
    
    Entity getEntityById(int entityId);

    String getPlayerNameByUUID(UUID uuid);

    Block getBlockAt(int x, int y, int z);

    Block getBlockAt(Location loc);
    
    int getDifficulty();
    
}
