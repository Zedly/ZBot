/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Creeper;
import zedly.zbot.event.Event;
import zedly.zbot.event.entity.CreeperChargeEvent;
import zedly.zbot.event.entity.CreeperFuseEvent;

/**
 *
 * @author Dennis
 */
public class CraftCreeper extends CraftMonster implements Creeper {

    protected int state = 0;
    protected boolean charged;
    protected boolean lit;
    
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if(metaMap.containsKey(12)) {
            state = metaMap.get(12).asInt();
        }
        if(metaMap.containsKey(13)) {
            charged = metaMap.get(13).asBoolean();
            if(charged) {
                list.add(new CreeperChargeEvent(this));
            }
        }
        if(metaMap.containsKey(14)) {
            lit = metaMap.get(14).asBoolean();
            list.add(new CreeperFuseEvent(this, lit));
        }
        return list;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.CREEPER;
    }

    @Override
    public boolean isIdle() {
        return state == -1;
    }

    @Override
    public boolean isCharged() {
        return charged;
    }

    @Override
    public boolean isIgnited() {
        return lit;
    }
    
}
