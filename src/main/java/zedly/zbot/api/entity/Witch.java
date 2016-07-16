package zedly.zbot.api.entity;

/**
 * Represents a witch.
 */
public interface Witch extends Monster
{

	/**
	 * @return whether this witch is currently aggressive
	 * (looking to attack a mob).
	 */
	boolean isAggressive();
}
