package zedly.zbot.api.entity;

import zedly.zbot.Location;

/**
 * Represents an ender crystal, found most commonly on end
 * towers.
 */
public interface EnderCrystal extends Entity
{
	/**
	 * @return the target of the beam from this ender crystal.
	 */
	Location getBeamTarget();

	/**
	 * @return whether this ender crystal has a target.
	 */
	boolean hasBeamTarget();
}
