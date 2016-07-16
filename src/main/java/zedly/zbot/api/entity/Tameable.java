package zedly.zbot.api.entity;

import java.util.UUID;

/**
 * Represents an entity that can be tamed.
 */
public interface Tameable extends Animal
{

	/**
	 * @return whether this tameable entity is sitting.
	 */
	boolean isSittng();

	/**
	 * @return whether this tameable entity is angry.
	 */
	boolean isAngry();

	/**
	 * @return whether this tameable entity is actually tamed.
	 */
	boolean isTamed();

	/**
	 * @return whether this tameable entity has an owner.
	 */
	boolean hasOwner();

	/**
	 * @return the UUID of this tameable entity's owner (not
	 * name, as if the owner changes their name the entity will
	 * no longer belong to them).
	 */
	UUID getOwner();
}
