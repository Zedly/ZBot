package zedly.zbot.api.entity;

/**
 * Represents a projectile, for example a snowball or arrow.
 */
public interface Projectile extends Object {

    /**
     * @return Whether or not this projectile has gravity
     */
    boolean hasGravity();
    
    /**
     * @return the ID of the entity that fired this projectile
     */
    int getFiringEntityId();
    
}
