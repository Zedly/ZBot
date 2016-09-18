/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.PrimedTNT;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftPrimedTNT extends CraftObject implements PrimedTNT {

    protected int fuseTicks;
    
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(6)) {
            fuseTicks = metaMap.get(6).asInt();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.FALLING_BLOCK;
    }

    @Override
    public synchronized int getFuseTime() {
        return fuseTicks;
    }
    
}
