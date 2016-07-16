/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.api.entity.Skeleton;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftSkeleton extends CraftMonster implements Skeleton {

    boolean witherSkeleton = false;
    boolean swingingArms = false;
    
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if(metaMap.containsKey(12)) {
            witherSkeleton = metaMap.get(12).asInt() != 0;
        }
        if(metaMap.containsKey(13)) {
            swingingArms = metaMap.get(13).asBoolean();
        }
        return list;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.SKELETON;
    }

    @Override
    public boolean isWitherSkeleton() {
        return witherSkeleton;
    }

    @Override
    public boolean isSwingingArms() {
        return swingingArms;
    }
    
}
