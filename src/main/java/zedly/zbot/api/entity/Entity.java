package zedly.zbot.api.entity;

import zedly.zbot.EntityType;
import zedly.zbot.Location;

/**
 * Represents any entity.
 */
public interface Entity
{
	/**
	 * @return the ID of this entity.
	 */
	int getEntityId();

	/**
	 * @return the location of this entity.
	 */
	Location getLocation();

	/**
	 * @return the type of this entity.
	 */
	EntityType getType();

	/**
	 * @return whether this entity is on fire.
	 */
	boolean isOnFire();

	/**
	 * @return whether this entity is sneaking.
	 */
	boolean isSneaking();

	/**
	 * @return whether this entity is sprinting.
	 */
	boolean isSprinting();

	/**
	 * @return whether this entity is eating.
	 */
	boolean isEating();

	/**
	 * @return whether this entity is invisible.
	 */
	boolean isInvisible();

	/**
	 * @return whether this entity is glowing.
	 */
	boolean isGlowing();

	/**
	 * @return whether this entity is gliding.
	 */
	boolean isGliding();

	/**
	 * @return how much air this entity has left (seen while underwater).
	 */
	int getAir();

	/**
	 * @return whether this entity has a custom name.
	 */
	boolean hasCustomName();

	/**
	 * @return this entity's custom name.
	 */
	String getCustomName();

	/**
	 * @return whether this entity's custom name is visible.
	 */
	boolean isNameVisible();

	/**
	 * @return whether this entity is silent.
	 */
	boolean isSilent();
}
