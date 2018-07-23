/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.inventory;

import zedly.zbot.GameContext;

/**
 *
 * @author Dennis
 */
public class CraftFurnaceInventory extends CraftExternalInventory implements FurnaceInventory {

    private int maxBurnTime = 0;
    private int remainingBurnTime = 0;
    private int maxProgress = 200;
    private int progress = 0;
    
    public CraftFurnaceInventory(GameContext context, int windowId, String title) {
        super(context, 39, 3, windowId, title);
    }
    
    @Override
    public String getTitle() {
        return "Furnace";
    }
    
        @Override
    public void setProperty(int property, int value) {
        
    }

    @Override
    public int getMaxBurnTime() {
        return maxBurnTime;
    }

    @Override
    public int getRemainingBurnTime() {
        return remainingBurnTime;
    }

    @Override
    public int getMaxProgress() {
        return maxProgress;
    }

    @Override
    public int getProgress() {
        return progress;
    }

    
}
