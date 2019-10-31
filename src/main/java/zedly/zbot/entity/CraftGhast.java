/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Ghast;
import zedly.zbot.event.Event;
import zedly.zbot.event.entity.GhastChargeEvent;
import zedly.zbot.inventory.CraftItemStack;

/**
 *
 * @author Dennis
 */
public class CraftGhast extends CraftFlying implements Ghast {

    protected boolean attacking;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(14)) {
            attacking = metaMap.get(14).asBoolean();
            if(attacking) {
                list.add(new GhastChargeEvent(this));
            }
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.GHAST;
    }

    @Override
    public synchronized boolean isAttacking() {
        return attacking;
    }

}
