package zedly.zbot.api.entity;

import zedly.zbot.WoolColor;

/**
 * Represents a sheep.
 */
public interface Sheep extends Animal
{
	/**
	 * @return the colour of this sheep.
	 */
	WoolColor getColor();

	/**
	 * @return if this sheep is sheared at the current time (if
	 * it has wool or not).
	 */
	boolean isSheared();
}
