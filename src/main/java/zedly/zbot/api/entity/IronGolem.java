package zedly.zbot.api.entity;

/**
 * Represents an iron golem.
 */
public interface IronGolem extends Golem
{
	/**
	 * @return whether this iron golem was created by a player.
	 */
	boolean isPlayerCreated();
}
