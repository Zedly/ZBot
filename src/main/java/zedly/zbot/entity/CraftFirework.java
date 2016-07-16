/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.api.entity.Firework;
import zedly.zbot.api.event.Event;
import zedly.zbot.api.inventory.ItemStack;

/**
 *
 * @author Dennis
 */
public class CraftFirework extends CraftEntity implements Firework {

    ItemStack fireworkItem;
    
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(6)) {
            fireworkItem = metaMap.get(6).asSlot();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.FIREWORK;
    }

    @Override
    public ItemStack getItemStack() {
        return fireworkItem;
    }
    
}
