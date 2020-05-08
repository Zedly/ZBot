/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Guardian;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftGuardian extends CraftMonster implements Guardian {

    protected boolean retractingSpikes = false;
    protected boolean elderly = false;
    protected int targetId = 0;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            elderly = metaMap.get(15).asBoolean();
        }
        if (metaMap.containsKey(16)) {
            targetId = metaMap.get(16).asInt();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.GUARDIAN;
    }

    @Override
    public synchronized boolean isRetractingSpikes() {
        return retractingSpikes;
    }

    @Override
    public synchronized boolean isTargeting() {
        return targetId != 0;
    }

    @Override
    public synchronized int getTargetedEntityId() {
        return targetId;
    }

}
