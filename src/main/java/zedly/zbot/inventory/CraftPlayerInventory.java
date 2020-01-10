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
public class CraftPlayerInventory extends CraftExternalInventory implements PlayerInventory {

    public CraftPlayerInventory(GameContext context) {
        super(context, 46, 9, 0, "Crafting");
    }

    public ItemStack getItemInOffHand() {
        return items[45];
    }
    
    @Override
    public void setProperty(int property, int value) {
    }
    
}
