/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public abstract class CraftAbstractFish extends CraftWaterMob implements AbstractFish {

    boolean fromBucket = false;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(14)) {
            fromBucket = metaMap.get(14).asBoolean();
        }
        return list;
    }
    
    @Override
    public boolean isFromBucket() {
        return fromBucket;
    }
    
}
