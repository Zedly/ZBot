/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.FishingHook;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftFishingHook extends CraftProjectile implements FishingHook {

    protected int hookedEntityId = 0;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if(metaMap.containsKey(7)) {
            hookedEntityId = metaMap.get(7).asInt() - 1;
        }
        return list;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.FISHING_BOBBER;
    }

    @Override
    public synchronized boolean hasHookedEntity() {
        return hookedEntityId != 0;
    }

    @Override
    public synchronized int getHookedEntityId() {
        return hookedEntityId;
    }
}
