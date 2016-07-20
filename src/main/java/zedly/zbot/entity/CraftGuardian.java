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
    
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if(metaMap.containsKey(12)) {
            int d = metaMap.get(12).asInt();
            retractingSpikes = (d & 0x02) != 0;
            elderly = (d & 0x04) != 0;
        }
        if(metaMap.containsKey(13)) {
            targetId = metaMap.get(13).asInt();
        }
        return list;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.GUARDIAN;
    }

    @Override
    public boolean isRetractingSpikes() {
        return retractingSpikes;
    }

    @Override
    public boolean isElderly() {
        return elderly;
    }

    @Override
    public boolean isTargeting() {
        return targetId != 0;
    }

    @Override
    public int getTargetedEntityId() {
        return targetId;
    }
    
}
