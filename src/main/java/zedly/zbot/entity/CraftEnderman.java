/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.CraftEntityMeta.ItemStackMeta;
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

    @Override
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(14)) {
            EntityMeta meta = metaMap.get(14);
            if (meta instanceof ItemStackMeta) {
                ItemStack newItemInHand = ((ItemStackMeta) meta).asSlot();
                list.add(new EndermanBlockChangeEvent(this, itemInHand, newItemInHand));
                itemInHand = newItemInHand;
            }
        }
        if (metaMap.containsKey(15)) {
            screaming = metaMap.get(15).asBoolean();
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
