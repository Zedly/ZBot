package zedly.zbot.api.entity;

import zedly.zbot.api.inventory.ItemStack;

/**
 * Represents an enderman.
 */
public interface Enderman extends Monster
{
	/**
	 * @return whether this enderman is carrying a block.
	 */
	boolean isCarryingBlock();

	/**
	 * @return the item stack of the block this enderman is carrying.
	 */
	ItemStack getItemInHand();

	/**
	 * @return whether this enderman is screaming.
	 */
	boolean isScreaming();
}
