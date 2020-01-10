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
public class CraftCraftingTableInventory extends CraftExternalInventory implements CraftingTableInventory {

    public CraftCraftingTableInventory(GameContext context, int windowId, String title) {
        super(context, 46, 10, windowId, title);
    }

    @Override
    public void setProperty(int property, int value) {
    }

}
