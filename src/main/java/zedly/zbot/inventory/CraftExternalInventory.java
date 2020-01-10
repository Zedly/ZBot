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
public abstract class CraftExternalInventory extends CraftInventory implements ExternalInventory {
    
    private final String title;
    
    public CraftExternalInventory(GameContext context, int size, int staticInventoryOffset, int windowId, String title) {
        super(context, size, staticInventoryOffset, windowId);
        this.title = title;
    }
    
        @Override
    public String getTitle() {
        return "Crafting";
    }
    
    @Override
    public ItemStack getHotbarSlot(int i) {
        return getSlot(staticBlockOffset + 27 + i);
    }
    
}
