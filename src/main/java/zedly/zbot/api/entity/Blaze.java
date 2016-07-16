package zedly.zbot.api.entity;

/**
 * Represents a blaze.
 */
public interface Blaze extends Monster
{
	/**
	 * @return whether this blaze is charging for an attack.
	 */
	boolean isCharging();
}
