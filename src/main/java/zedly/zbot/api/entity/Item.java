package zedly.zbot.api.entity;

import zedly.zbot.api.inventory.ItemStack;

/**
 * Represents an item, for example on the ground.
 */
public interface Item {

    /**
     * @return the item stack of this item.
     */
    ItemStack getItemStack();
}
