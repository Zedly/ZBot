/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.api.entity.Wither;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftWither extends CraftMonster implements Wither {

    int target1 = 0, target2 = 0, target3 = 0, invulnerableTime = 0;

    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(12)) {
            target1 = metaMap.get(12).asInt();
        }
        if (metaMap.containsKey(13)) {
            target2 = metaMap.get(13).asInt();
        }
        if (metaMap.containsKey(14)) {
            target3 = metaMap.get(14).asInt();
        }
        if (metaMap.containsKey(15)) {
            invulnerableTime = metaMap.get(15).asInt();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.FISHING_FLOAT;
    }

    @Override
    public boolean isFirstHeadTargeting() {
        return target1 != 0;
    }

    @Override
    public int getFirstHeadTargetId() {
        return target1;
    }

    @Override
    public boolean isSecondHeadTargeting() {
        return target3 != 0;
    }

    @Override
    public int getSecondHeadTargetId() {
        return target2;
    }

    @Override
    public boolean isThirdHeadTargeting() {
        return target3 != 0;
    }

    @Override
    public int getThirdHeadTargetId() {
        return target3;
    }

    @Override
    public int[] getTargetIds() {
        return new int[] {target1, target2, target3};
    }

    @Override
    public int getInvulnerableTime() {
        return invulnerableTime;
    }

}
