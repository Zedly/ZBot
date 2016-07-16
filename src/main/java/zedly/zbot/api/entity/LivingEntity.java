package zedly.zbot.api.entity;

/**
 * Represents any living entity in the game.
 */
public interface LivingEntity extends Entity
{
	/**
	 * @return whether this entity's left hand is in use.
	 */
	boolean isLeftHandActive();

	/**
	 * @return whether this entity's main hand is in use.
	 */
	boolean isHandActive();

	/**
	 * @return the health of this entity.
	 */
	float getHealth();

	/**
	 * @return the potion effect colour of this entity.
	 */
	int getPotionEffectColor();

	/**
	 * @return whether a potion effect on this entity is ambient.
	 */
	boolean isPotionEffectAmbient();

	/**
	 * @return the number of arrows stuck to this entity.
	 */
	int getArrowsStuck();
}
