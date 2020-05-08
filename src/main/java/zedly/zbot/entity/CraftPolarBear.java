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
public class CraftPolarBear extends CraftAnimal implements PolarBear {

    boolean standingUp;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(16)) {
            standingUp = metaMap.get(16).asBoolean();
        }
        return list;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.POLAR_BEAR;
    }

    @Override
    public boolean isStandingUp() {
        return standingUp;
    }
    
}
