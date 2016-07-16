/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.api.entity.Witch;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftWitch extends CraftMonster implements Witch {

    protected boolean aggressive;
    
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if(metaMap.containsKey(12)) {
            aggressive = metaMap.get(12).asBoolean();
        }
        return list;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.WITCH;
    }

    @Override
    public boolean isAggressive() {
        return aggressive;
    }
    
}
