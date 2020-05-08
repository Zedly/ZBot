/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.entity.Ageable;
import zedly.zbot.event.Event;
import zedly.zbot.event.entity.EntityGrowUpEvent;

/**
 *
 * @author Dennis
 */
public abstract class CraftAgeable extends CraftCreature implements Ageable {
    
    protected boolean baby = false;
    
    @Override
     public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if(metaMap.containsKey(15)) {
            boolean nbaby = metaMap.get(15).asBoolean();
            if(baby && !nbaby) {
                list.add(new EntityGrowUpEvent(this));
            }
            baby = nbaby;
        }
        return list;
    }
    
    @Override
    public synchronized boolean isBaby() {
        return baby;
    }
    
}
