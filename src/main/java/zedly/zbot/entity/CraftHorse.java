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
public class CraftHorse extends CraftAbstractHorse implements Horse {

    private int variant;
    private int armor;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            variant  = metaMap.get(15).asInt();
        }
        if (metaMap.containsKey(16)) {
            armor = metaMap.get(16).asInt();
        }
        return list;
    }

    public EntityType getType() {
        return EntityType.HORSE;
    }
    
    
    @Override
    public int getVariant() {
        return variant;
    }

    @Override
    public int getArmor() {
        return armor;
    }
    
}
