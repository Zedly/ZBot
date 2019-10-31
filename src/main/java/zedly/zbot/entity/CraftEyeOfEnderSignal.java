/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import org.bukkit.Material;
import zedly.zbot.inventory.CraftItemStack;

/**
 *
 * @author Dennis
 */
public class CraftEyeOfEnderSignal extends CraftItemedThrowable implements EyeOfEnderSignal {

    public CraftEyeOfEnderSignal() {
        super(new CraftItemStack(Material.EYE_OF_ENDER));
    }
    
    @Override
    public EntityType getType() {
        return EntityType.ENDER_SIGNAL;
    }
}
