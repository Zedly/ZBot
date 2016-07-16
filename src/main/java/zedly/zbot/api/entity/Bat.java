package zedly.zbot.api.entity;

/**
 * Represents a bat.
 */
public interface Bat extends Ambient
{
	/**
	 * @return whether this bat is hanging from a surface.
	 */
	boolean isHanging();
}
