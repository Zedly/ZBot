/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.api.event.entity;

import zedly.zbot.api.entity.Enderman;
import zedly.zbot.api.event.Event;
import zedly.zbot.api.inventory.ItemStack;

/**
 *
 * @author Dennis
 */
public class EndermanBlockChangeEvent extends Event {
    private final Enderman enderman;
    private final ItemStack oldItem;
    private final ItemStack newItem;

    public EndermanBlockChangeEvent(Enderman enderman, ItemStack oldItem, ItemStack newItem) {
        this.enderman = enderman;
        this.oldItem = oldItem;
        this.newItem = newItem;
    }

    public Enderman getEntity() {
        return enderman;
    }
    
    public ItemStack getOldItemInHand() {
        return oldItem;
    }
    
    public ItemStack getNewItemInHand() {
        return newItem;
    }
}
