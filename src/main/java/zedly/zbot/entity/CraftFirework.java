/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Firework;
import zedly.zbot.event.Event;
import zedly.zbot.inventory.ItemStack;

/**
 *
 * @author Dennis
 */
public class CraftFirework extends CraftObject implements Firework {

    ItemStack fireworkItem;
    int shooterEntityId;
    boolean shotAtAngle;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(7)) {
            fireworkItem = metaMap.get(7).asSlot();
        }
        if (metaMap.containsKey(8)) {
            shooterEntityId = metaMap.get(8).asInt();
        }
        if (metaMap.containsKey(9)) {
            shotAtAngle = metaMap.get(9).asBoolean();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.FIREWORK_ROCKET;
    }

    @Override
    public synchronized ItemStack getItemStack() {
        return fireworkItem;
    }

    @Override
    public boolean hasGravity() {
        return false;
    }

    @Override
    public int getUserEntityId() {
        return shooterEntityId;
    }

    @Override
    public boolean isShotAtAngle() {
        return shotAtAngle;
    }

}
