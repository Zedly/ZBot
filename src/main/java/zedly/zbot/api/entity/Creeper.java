package zedly.zbot.api.entity;

/**
 * Represents a creeper.
 */
public interface Creeper extends Monster
{
	/**
	 * @return if this creeper is currently idle (not about to
	 * explode).
	 */
	boolean isIdle();

	/**
	 * @return if this creeper is a charged creeper.
	 */
	boolean isCharged();

	/**
	 * @return whether this creeper is ignited and about to
	 * explode.
	 */
	boolean isIgnited();
}
