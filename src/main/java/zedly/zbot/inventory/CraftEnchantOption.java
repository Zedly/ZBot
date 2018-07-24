/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.inventory;

/**
 *
 * @author Dennis
 */
public class CraftEnchantOption implements EnchantOption {
   
    private final int levelCost;
    private final Enchantment enchantment;
    private final int level;
    
    public CraftEnchantOption(int levelCost, Enchantment enchantment, int level) {
        this.level = level;
        this.enchantment = enchantment;
        this.levelCost = levelCost;
    }

    @Override
    public int getLevelCost() {
        return levelCost;
    }
    @Override
    public Enchantment getEnchantment() {
        return enchantment;
    }

    @Override
    public int getLevel() {
        return level;
    }
    
    
}
