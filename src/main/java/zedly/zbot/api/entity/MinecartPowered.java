package zedly.zbot.api.entity;

/**
 * Represents a minecart powered by a furnace.
 */
public interface MinecartPowered extends Minecart
{
	/**
	 * @return whether the furnace in this minecart is currently
	 * powering it.
	 */
	boolean isPowered();
}
