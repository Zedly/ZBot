/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.api.entity.Item;
import zedly.zbot.api.event.Event;
import zedly.zbot.api.inventory.ItemStack;

/**
 *
 * @author Dennis
 */
public class CraftItem extends CraftObject implements Item {

    protected ItemStack itemStack;
    
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(6)) {
            itemStack = metaMap.get(6).asSlot();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.ITEM;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }
}
