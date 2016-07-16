package zedly.zbot.api.entity;

import zedly.zbot.api.inventory.ItemStack;

/**
 * Represents a minecart.
 */
public interface Minecart extends Entity
{
	/**
	 * @return the amount of shaking this minecart currently has
	 * in order to break it.
	 */
	int getShakingPower();

	/**
	 * @return the direction of the shaking of this minecart.
	 */
	int getShakeDirection();

	/**
	 * @return the shaking multiplier of this minecart (similar
	 * to a combo in a game).
	 */
	float getShakingMultiplier();

	/**
	 * @return the block of this minecart.
	 */
	ItemStack getBlock();

	/**
	 * @return this minecart's Y block position. For some reason
	 * there is no getLocation method. Seems legit.
	 */
	int getBlockYPosition();

	/**
	 * @return whether this minecart is visible.
	 */
	boolean isBlockVisibile();
}
