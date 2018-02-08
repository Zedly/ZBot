/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot;

import zedly.zbot.inventory.ItemStack;

/**
 *
 * @author Dennis
 */
public class AdvancementDisplay {
    
    private final String title;
    private final String description;
    private final ItemStack slot;
    private final int frameType;
    private final int flags;
    private final String backgroundTexture;
    private final double xCoord;
    private final double yCoord;
    
    public AdvancementDisplay(String title, String description, ItemStack slot, 
            int frameType, int flags, String backgroundTexture, double xCoord, double yCoord) {
        this.title = title;
        this.description = description;
        this.slot = slot;
        this.frameType = frameType;
        this.flags = flags;
        this.backgroundTexture = backgroundTexture;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }
    
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ItemStack getSlot() {
        return slot;
    }

    public int getFrameType() {
        return frameType;
    }

    public int getFlags() {
        return flags;
    }

    public String getBackgroundTexture() {
        return backgroundTexture;
    }

    public double getxCoord() {
        return xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }
}
