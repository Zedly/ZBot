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
import zedly.zbot.block.Material;
import zedly.zbot.entity.FallingBlock;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftFallingBlock extends CraftObject implements FallingBlock {
    
    protected Material material = Material.AIR;
    protected int blockData = 0;
    protected Location spawnLocation = new Location(0, 0, 0);
    
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if(metaMap.containsKey(6)) {
            location = metaMap.get(6).asLocation();
        }
        //do additional stuff
        return list;
    }
    
    public void setObjectData(int objectData) {
        super.setObjectData(objectData);
        material = Material.fromTypeId(objectData & 0xFFF);
        blockData = objectData >> 12;
    }
    
    public Location getSpawnLocation() {
        return spawnLocation;
    }
    
    public EntityType getType() {
        return EntityType.FALLING_BLOCK;
    }
    
    public Material getBlockType() {
        return material;
    }
    
    public int getBlockData() {
        return blockData;
    }
    
}
