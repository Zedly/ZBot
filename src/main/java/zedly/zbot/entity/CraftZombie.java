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
public class CraftZombie extends CraftMonster implements Zombie {

    protected boolean baby = false;
    protected boolean drowning = false;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(17)) {
            baby = metaMap.get(17).asBoolean();
        }
        if (metaMap.containsKey(18)) {
            drowning = metaMap.get(18).asBoolean();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.ZOMBIE;
    }

    @Override
    public synchronized boolean isBaby() {
        return baby;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }

    @Override
    public boolean isDrowning() {
        return drowning;
    }
}
