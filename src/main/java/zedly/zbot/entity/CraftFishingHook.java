/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.api.entity.FishingHook;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftFishingHook extends CraftProjectile implements FishingHook {

    protected int hookedEntityId = 0;
    
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if(metaMap.containsKey(6)) {
            hookedEntityId = metaMap.get(6).asInt();
        }
        return list;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.FISHING_FLOAT;
    }

    @Override
    public boolean hasHookedEntity() {
        return hookedEntityId != 0;
    }

    @Override
    public int getHookedEntityId() {
        return hookedEntityId;
    }   
    
    public boolean hasGravity() {
        return true;
    }
    
}
