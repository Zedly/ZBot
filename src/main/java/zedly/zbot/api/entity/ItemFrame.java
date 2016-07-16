package zedly.zbot.api.entity;

import zedly.zbot.api.inventory.ItemStack;

/**
 * Represents an item frame (a frame for items).
 */
public interface ItemFrame extends Hanging {

    /**
     * @return the item stack inside this item frame.
     */
    ItemStack getItemStack();

    /**
     * @return the rotation of the itemstack inside of this item frame.
     */
    int getRotation();
}
