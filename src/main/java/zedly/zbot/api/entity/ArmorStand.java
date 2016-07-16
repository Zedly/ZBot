package zedly.zbot.api.entity;

/**
 * Represents an armour stand.
 */
public interface ArmorStand extends LivingEntity
{
	/**
	 * @return whether this armour stand is small.
	 */
	boolean isSmall();

	/**
	 * @return whether this armour stand has gravity.
	 */
	boolean hasGravity();

	/**
	 * @return whether this armour stand has arms.
	 */
	boolean hasArms();

	/**
	 * @return whether this armour stand has a plate on the
	 * bottom.
	 */
	boolean hasBasePlate();

	/**
	 * @return whether this armour stand has a marker.
	 */
	boolean hasMarker();

	/**
	 * @return the rotation of this armour stand's head.
	 */
	float[] getHeadRotation();

	/**
	 * @return the rotation of this armour stand's body.
	 */
	float[] getBodyRotation();

	/**
	 * @return the rotation of this armour stand's left arm.
	 */
	float[] getLeftArmRotation();

	/**
	 * @return the rotation of this armour stand's right arm.
	 */
	float[] getRightArmRotation();

	/**
	 * @return the rotation of this armour stand's left leg.
	 */
	float[] getLeftLegRotation();

	/**
	 * @return the rotation of this armour stand's right leg.
	 */
	float[] getRightLegRotation();

}
