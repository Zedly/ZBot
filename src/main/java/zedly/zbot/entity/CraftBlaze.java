/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Blaze;
import zedly.zbot.event.Event;
import zedly.zbot.event.entity.BlazeChargeEvent;

/**
 *
 * @author Dennis
 */
public class CraftBlaze extends CraftMonster implements Blaze {

    protected boolean charging;

    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(12)) {
            charging = (metaMap.get(12).asInt() & 0x01) == 0x01;
            if (charging) {
                list.add(new BlazeChargeEvent(this));
            }
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.BLAZE;
    }

    @Override
    public synchronized boolean isCharging() {
        return charging;
    }

}
