package zedly.zbot.api.entity;

import java.util.UUID;

/**
 * Represents a player.
 */
public interface Player extends LivingEntity
{
	/**
	 * @return the name of this player.
	 */
	String getName();

	/**
	 * @return the unique ID of this player.
	 */
	UUID getUUID();

	/**
	 * @return the health in this player's additional hearts.
	 */
	float getAdditionalHearts();

	/**
	 * @return this player's score (most commonly seen in the
	 * death screen).
	 */
	int getScore();

	/**
	 * @return this player's skin flags.
	 */
	int getSkinFlags();

	/**
	 * @return whether this player is left handed (in game obviously).
	 */
	boolean isLeftHanded();
}
