package zedly.zbot.api.entity;

import zedly.zbot.WoolColor;

/**
 * Represents a wolf.
 */
public interface Wolf extends Tameable {

	/**
     * @return the last damage this wolf took.
     */
    float getDamageTaken();

	/**
     * @return whether this wolf is currently begging.
     */
    boolean isBegging();

	/**
     * @return the colour of this wolf's collar.
     */
    WoolColor getCollarColor();
}
