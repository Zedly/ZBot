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
public class CraftTrident extends CraftArrow implements Trident {
    
    protected int loyaltyLevel = 0;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(10)) {
            loyaltyLevel = metaMap.get(10).asInt();
        }
        return list;
    }

    @Override
    public int getLoyaltyLevel() {
        return loyaltyLevel;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.TRIDENT;
    }
}
