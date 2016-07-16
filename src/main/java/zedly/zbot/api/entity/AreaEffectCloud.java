package zedly.zbot.api.entity;

/**
 * Represents a particle effect.
 */
public interface AreaEffectCloud extends Entity
{
	/**
	 * @return the radius of the effect.
	 */
	float getRadius();

	/**
	 * @return the colour of particles used in the effect.
	 */
	int getColorId();

	/**
	 * @return whether the effect is focused on a point.
	 */
	boolean isPoint();

	/**
	 * @return the ID of the particles in the effect.
	 */
	int getParticleId();
}
