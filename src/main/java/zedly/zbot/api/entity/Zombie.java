package zedly.zbot.api.entity;

import zedly.zbot.VillagerProfession;

/**
 * Represents a zombie.
 */
public interface Zombie extends Monster {

	/**
     * @return whether this zombie is a baby zombie.
     */
    boolean isBaby();

	/**
     * @return whether this zombie is a zombie villager.
     */
    boolean isVillager();

	/**
     * @return the profession of this zombie, if it is a zombie villager.
     */
    VillagerProfession getProfession();

	/**
     * @return whether this zombie is converting.
     */
    boolean isConverting();

	/**
     * @return whether this zombie is holding its hands up.
     */
    boolean isHoldingHandsUp();
}
