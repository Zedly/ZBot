package zedly.zbot.api.entity;

/**
 * Represents a snwowman (also known as a snow golem).
 */
public interface Snowman extends Golem
{
	/**
	 * @return if this snowman has a pumpkin for its head.
	 */
	boolean hasPumpkinHead();
}
