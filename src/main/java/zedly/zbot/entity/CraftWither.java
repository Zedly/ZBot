/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Wither;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftWither extends CraftMonster implements Wither {

    int target1 = 0, target2 = 0, target3 = 0, invulnerableTime = 0;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            target1 = metaMap.get(15).asInt();
        }
        if (metaMap.containsKey(16)) {
            target2 = metaMap.get(16).asInt();
        }
        if (metaMap.containsKey(17)) {
            target3 = metaMap.get(17).asInt();
        }
        if (metaMap.containsKey(18)) {
            invulnerableTime = metaMap.get(18).asInt();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.WITHER;
    }

    @Override
    public synchronized boolean isCenterHeadTargeting() {
        return target1 != 0;
    }

    @Override
    public synchronized int getCenterHeadTargetId() {
        return target1;
    }

    @Override
    public synchronized boolean isLeftHeadTargeting() {
        return target3 != 0;
    }

    @Override
    public synchronized int getLeftHeadTargetId() {
        return target2;
    }

    @Override
    public synchronized boolean isRightHeadTargeting() {
        return target3 != 0;
    }

    @Override
    public synchronized int getRightHeadTargetId() {
        return target3;
    }

    @Override
    public synchronized int[] getTargetIds() {
        return new int[] {target1, target2, target3};
    }

    @Override
    public synchronized int getInvulnerableTime() {
        return invulnerableTime;
    }

}
