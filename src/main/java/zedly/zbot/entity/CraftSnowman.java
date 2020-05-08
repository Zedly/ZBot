/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Snowman;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftSnowman extends CraftGolem implements Snowman {

    protected boolean hasPumpkinHead = true;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            hasPumpkinHead = (metaMap.get(15).asInt() & 0x10) != 0;
        }
        return list;
    }
    
    public EntityType getType() {
        return EntityType.SNOW_GOLEM;
    }

    @Override
    public synchronized boolean hasPumpkinHead() {
        return hasPumpkinHead;
    }
    
}
