package zedly.zbot.api.entity;

/**
 * Represents a ghast.
 */
public interface Ghast extends Flying
{
	/**
	 * @return if this ghast is attacking an object.
	 */
	boolean isAttacking();
}
