package zedly.zbot.api.entity;

import zedly.zbot.api.inventory.ItemStack;

/**
 * Represents a potion.
 */
public interface Potion extends Entity
{
	/**
	 * @return the item stack this potion came from.
	 */
	ItemStack getItemStack();
}
