/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.event.Event;
import zedly.zbot.inventory.ItemStack;

/**
 *
 * @author Dennis
 */
public abstract class CraftItemedFireball extends CraftObject implements ItemedFireball {
    protected ItemStack item;

    protected CraftItemedFireball(ItemStack item) {
        this.item = item;
    }

    public CraftItemedFireball() {
    }

    @Override
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> events = super.setMeta(metaMap);
        if (metaMap.containsKey(7)) {
            item = metaMap.get(7).asSlot();
        }
        return events;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }
}
