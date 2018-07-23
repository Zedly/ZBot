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
public class CraftChestInventory extends CraftExternalInventory implements ChestInventory {

    public CraftChestInventory(GameContext context, int windowId, int size, String title) {
        super(context, size + 36, size, windowId, title);
    }

    @Override
    public String getTitle() {
        return "Chest";
    }
    
    @Override
    public void setProperty(int property, int value) {
    }
    
}
