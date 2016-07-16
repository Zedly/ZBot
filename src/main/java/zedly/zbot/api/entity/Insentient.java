package zedly.zbot.api.entity;

/**
 * Represents an insentient entity (one which is not controlled).
 */
public interface Insentient extends LivingEntity
{
	/**
	 * @return whether this insentient entity has an AI.
	 */
	boolean hasAI();

	/**
	 * @return whether this insentient entity is left handed.
	 */
	boolean isLeftHanded();
}
