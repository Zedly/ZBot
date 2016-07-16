package zedly.zbot.api.entity;

/**
 * Represents a wither.
 */
public interface Wither
{
	/**
	 * @return if the main head of this wither is targeting
	 * anything.
	 */
	boolean isFirstHeadTargeting();

	/**
	 * @return the entity ID of the entity this wither's main
	 * head is targeting.
	 */
	int getFirstHeadTargetId();

	/**
	 * @return if the second head of this wither is targeting
	 * anything.
	 */
	boolean isSecondHeadTargeting();

	/**
	 * @return the entity ID of the entity this wither's second
	 * head is targeting.
	 */
	int getSecondHeadTargetId();

	/**
	 * @return the entity ID of the entity this wither's third
	 * head is targeting.
	 */
	boolean isThirdHeadTargeting();

	/**
	 * @return the entity ID of the entity this wither's third
	 * head is targeting.
	 */
	int getThirdHeadTargetId();

	/**
	 * @return all of the entity IDs of the targets of this
	 * wither.
	 */
	int[] getTargetIds();

	/**
	 * @return how long this wither is invulnerable for.
	 */
	int getInvulnerableTime();

}
