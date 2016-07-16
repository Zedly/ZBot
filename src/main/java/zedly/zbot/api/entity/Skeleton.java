package zedly.zbot.api.entity;

/**
 * Represents a skeleton.
 */
public interface Skeleton extends Monster
{
	/**
	 * @return whether this skeleton is a wither skeleton.
	 */
	boolean isWitherSkeleton();

	/**
	 * @return whether this skeleton is swinging its arms.
	 */
	boolean isSwingingArms();
}
