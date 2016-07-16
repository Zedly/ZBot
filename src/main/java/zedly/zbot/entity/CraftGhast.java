/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.api.entity.Ghast;
import zedly.zbot.api.event.Event;
import zedly.zbot.api.event.entity.GhastChargeEvent;
import zedly.zbot.inventory.CraftItemStack;

/**
 *
 * @author Dennis
 */
public class CraftGhast extends CraftFlying implements Ghast {

    protected boolean attacking;
    
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(12)) {
            attacking = metaMap.get(12).asBoolean();
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
    public boolean isAttacking() {
        return attacking;
    }

}
