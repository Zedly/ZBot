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
public class ZTrade implements Trade {

    private final ItemStack input1;
    private final ItemStack input2;
    private final ItemStack output;
    private final boolean enabled;
    private final int numTradeUses;
    private final int maxTradeUses;
    private final int xpReward;
    private final int specialPrice;
    private final double priceMultiplier;
    private final int demand;

    public ZTrade(ItemStack input1, ItemStack input2, ItemStack output, boolean enabled, int numTradeUses, int maxTradeUses, int xpReward, int specialPrice, double priceMultiplier, int demand) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.enabled = enabled;
        this.numTradeUses = numTradeUses;
        this.maxTradeUses = maxTradeUses;
        this.xpReward = xpReward;
        this.specialPrice = specialPrice;
        this.priceMultiplier = priceMultiplier;
        this.demand = demand;
    }
    
    
    @Override
    public ItemStack getInput1() {
        return input1;
    }

    @Override
    public ItemStack getInput2() {
        return input2;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public int getNumTradeUses() {
        return numTradeUses;
    }

    @Override
    public int getMaxTradeUses() {
        return maxTradeUses;
    }

    @Override
    public int getXPReward() {
        return xpReward;
    }

    @Override
    public int getSpecialPrice() {
        return specialPrice;
    }

    @Override
    public double getPriceMultiplier() {
        return priceMultiplier;
    }

    @Override
    public int getDemand() {
        return demand;
    }

}
