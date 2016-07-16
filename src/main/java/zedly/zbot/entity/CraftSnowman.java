/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.api.entity.Snowman;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftSnowman extends CraftGolem implements Snowman {

    protected boolean hasPumpkinHead = true;
    
    @Override
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(11)) {
            hasPumpkinHead = (metaMap.get(11).asInt() & 0x10) != 0;
        }
        return list;
    }
    
    public EntityType getType() {
        return EntityType.SNOWMAN;
    }

    @Override
    public boolean hasPumpkinHead() {
        return hasPumpkinHead;
    }
    
}
