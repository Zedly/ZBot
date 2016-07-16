package zedly.zbot.api.entity;

/**
 * Represents an arrow shot by an object.
 */
public interface Arrow extends Projectile {

    /**
     * @return if the damage done by this arrow is critical.
     */
    boolean isCritical();
}
