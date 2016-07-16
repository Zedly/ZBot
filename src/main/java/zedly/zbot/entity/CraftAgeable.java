/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.api.entity.Ageable;
import zedly.zbot.api.event.Event;
import zedly.zbot.api.event.entity.EntityGrowUpEvent;

/**
 *
 * @author Dennis
 */
public abstract class CraftAgeable extends CraftCreature implements Ageable {
    
    protected boolean baby = false;
    
     public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if(metaMap.containsKey(12)) {
            boolean nbaby = metaMap.get(12).asBoolean();
            if(baby && !nbaby) {
                list.add(new EntityGrowUpEvent(this));
            }
            baby = nbaby;
        }
        return list;
    }
    
    public boolean isBaby() {
        return baby;
    }
    
}
