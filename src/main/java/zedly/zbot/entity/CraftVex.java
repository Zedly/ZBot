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
public class CraftVex extends CraftMonster implements Vex {

    boolean attacking = false;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(14)) {
            attacking = metaMap.get(14).asBoolean();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.VEX;
    }

    @Override
    public boolean isAttacking() {
        return attacking;
    }

}
