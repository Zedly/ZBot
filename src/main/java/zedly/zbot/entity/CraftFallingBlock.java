/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.Location;
import zedly.zbot.Material;
import zedly.zbot.event.Event;
import zedly.zbot.network.mappings.MaterialIds;

/**
 *
 * @author Dennis
 */
public class CraftFallingBlock extends CraftObject implements FallingBlock {
    
    protected Material material = Material.AIR;
    protected int blockData = 0;
    protected Location spawnLocation = new Location(0, 0, 0);
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if(metaMap.containsKey(7)) {
            location = metaMap.get(7).asLocation();
        }
        return list;
    }
    
    @Override
    public synchronized void setObjectData(int objectData) {
        super.setObjectData(objectData);
        material = MaterialIds.fromBlockId(objectData);
        blockData = objectData >> 12;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.FALLING_BLOCK;
    }
    
    @Override
    public synchronized Material getBlockType() {
        return material;
    }

    @Override
    public Location getSpawnPosition() {
        return spawnLocation;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }
    
}
