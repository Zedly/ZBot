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
import zedly.zbot.event.entity.EndermanBlockChangeEvent;
import zedly.zbot.event.entity.EndermanScreamEvent;
import zedly.zbot.inventory.ItemStack;
import zedly.zbot.inventory.CraftItemStack;

/**
 *
 * @author Dennis
 */
public class CraftEnderman extends CraftMonster implements Enderman {

    protected ItemStack itemInHand;
    protected boolean screaming;

    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(12)) {
            int d = metaMap.get(12).asInt();
            ItemStack newItemInHand = new CraftItemStack(d >> 4, d & 0xF);
            list.add(new EndermanBlockChangeEvent(this, itemInHand, newItemInHand));
            itemInHand = newItemInHand;
        }
        if (metaMap.containsKey(13)) {
            screaming = metaMap.get(13).asBoolean();
            if (screaming) {
                list.add(new EndermanScreamEvent(this));
            }
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.ENDERMAN;
    }

    @Override
    public boolean isCarryingBlock() {
        return itemInHand != null;
    }

    @Override
    public ItemStack getItemInHand() {
        return itemInHand;
    }

    @Override
    public boolean isScreaming() {
        return screaming;
    }

}
