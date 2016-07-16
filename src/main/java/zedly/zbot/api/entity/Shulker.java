package zedly.zbot.api.entity;

import zedly.zbot.Direction;
import zedly.zbot.Location;

/**
 * Represents a shulker.
 */
public interface Shulker extends Golem
{
	/**
	 * @return the direction this shulker is facing.
	 */
	Direction getDirection();

	/**
	 * @return the location this shulker attached to.
	 */
	Location getAttachedLocation();

	/**
	 * @return the height of this shulker's shield (the cover it
	 * has).
	 */
	int getShieldHeight();
}
