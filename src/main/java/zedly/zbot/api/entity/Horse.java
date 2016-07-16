package zedly.zbot.api.entity;

import java.util.UUID;

/**
 * Represents a horse.
 */
public interface Horse extends Animal
{
	/**
	 * @return whether this horse is tamed.
	 */
	boolean isTame();

	/**
	 * @return whether this horse has a saddle.
	 */
	boolean isSaddled();

	/**
	 * @return whether this horse has a chest (donkeys only).
	 */
	boolean hasChest();

	/**
	 * @return whether this horse has been bred.
	 */
	boolean isBred();

	/**
	 * @return whether this horse is eating.
	 */
	boolean isEating();

	/**
	 * @return whether this horse is rearing (on its hind legs; doing a wheelie).
	 */
	boolean isRearing();

	/**
	 * @return whether this horse's mouth is open.
	 */
	boolean isMouthOpen();

	//TODO: Implement enum

	/**
	 * @return the variant of this horse.
	 */
	int getVariant();

	/**
	 * @return the style of this horse's markings.
	 */
	int getStyle();

	boolean hasOwner();

	/**
	 * @return the UUID of this horse's owner - not name, as if
	 * they change their name this horse will no longer belong
	 * to them.
	 */
	UUID getOwner();

	//TODO: Implement enum

	/**
	 * @return the armour this horse is wearing.
	 */
	int getArmor();
}
