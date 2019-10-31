/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftSpider extends CraftMonster implements Spider {

    protected boolean climbing;
    
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if(metaMap.containsKey(15)) {
            climbing = metaMap.get(15).asInt() != 0;
        }
        return list;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.SPIDER;
    }

    @Override
    public synchronized boolean isClimbing() {
        return climbing;
    }
}
