/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.ItemFrame;
import zedly.zbot.event.Event;
import zedly.zbot.event.entity.ItemFrameContentEvent;
import zedly.zbot.inventory.ItemStack;

/**
 *
 * @author Dennis
 */
public class CraftItemFrame extends CraftHanging implements ItemFrame {

    protected ItemStack itemStack;
    protected int rotation;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(7)) {
            ItemStack nItemStack = metaMap.get(7).asSlot();
            list.add(new ItemFrameContentEvent(this, itemStack, nItemStack));
            itemStack = nItemStack;
        }
        if (metaMap.containsKey(8)) {
            rotation = metaMap.get(8).asInt();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.ITEM_FRAME;
    }

    @Override
    public synchronized ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public synchronized int getRotation() {
        return rotation;
    }

}
