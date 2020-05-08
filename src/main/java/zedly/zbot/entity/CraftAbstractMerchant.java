/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public abstract class CraftAbstractMerchant extends CraftAgeable implements AbstractMerchant {
    
    int headShakeTimer;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(16)) {
            headShakeTimer = metaMap.get(16).asInt();
        }
        return list;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }

    @Override
    public int getHeadShakeTimer() {
        return headShakeTimer;
    }
}
