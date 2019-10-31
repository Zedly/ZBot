/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.IronGolem;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftVillagerGolem extends CraftGolem implements IronGolem {

    boolean playerCreated = false;
    boolean isHidingRose = false;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(13)) {
            playerCreated = metaMap.get(13).asInt() != 0;
        }
        return list;
    }
    
    public EntityType getType() {
        return EntityType.IRON_GOLEM;
    }

    @Override
    public synchronized boolean isPlayerCreated() {
        return playerCreated;
    }
    
    
    public synchronized boolean isHidingRose() {
        return isHidingRose;
    }
    
}
