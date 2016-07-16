/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.api.entity.CaveSpider;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftCaveSpider extends CraftMonster implements CaveSpider {
    
    protected boolean climbing;
    
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if(metaMap.containsKey(12)) {
            climbing = metaMap.get(12).asInt() != 0;
        }
        return list;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.SPIDER;
    }

    @Override
    public boolean isClimbing() {
        return climbing;
    }
    
}
