/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.api.entity.ItemFrame;
import zedly.zbot.api.event.Event;
import zedly.zbot.api.event.entity.ItemFrameContentEvent;
import zedly.zbot.api.inventory.ItemStack;

/**
 *
 * @author Dennis
 */
public class CraftItemFrame extends CraftHanging implements ItemFrame {

    protected ItemStack itemStack;
    protected int rotation;
    
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(6)) {
            ItemStack nItemStack = metaMap.get(6).asSlot();
            list.add(new ItemFrameContentEvent(this, itemStack, nItemStack));
            itemStack = nItemStack;
        }
        if (metaMap.containsKey(7)) {
            rotation = metaMap.get(7).asInt();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.ITEM_FRAME;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public int getRotation() {
        return rotation;
    }

}
