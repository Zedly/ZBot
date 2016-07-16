package zedly.zbot.api.entity;

/**
 * Represents primed tnt (tnt which is going to blow up).
 */
public interface PrimedTNT extends Entity
{
	/**
	 * @return the time until this primed tnt explodes.
	 */
	int getFuseTime();
}
