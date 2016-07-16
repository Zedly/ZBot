package zedly.zbot.api.entity;

import zedly.zbot.api.inventory.ItemStack;

/**
 * Represents a firework.
 */
public interface Firework extends Entity
{
	/**
	 * @return the ItemStack this firework came from.
	 */
	ItemStack getItemStack();
}
