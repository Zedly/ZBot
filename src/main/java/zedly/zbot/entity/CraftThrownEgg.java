/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import org.bukkit.Material;
import zedly.zbot.EntityType;
import zedly.zbot.inventory.CraftItemStack;

/**
 *
 * @author Dennis
 */
public class CraftThrownEgg extends CraftItemedThrowable implements ThrownEgg {

    public CraftThrownEgg() {
        super(new CraftItemStack(Material.EGG));
    }
    
    @Override
    public EntityType getType() {
        return EntityType.EGG;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }
}
