/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.Material;
import zedly.zbot.inventory.CraftItemStack;

/**
 *
 * @author Dennis
 */
public class CraftEyeOfEnderSignal extends CraftItemedThrowable implements EyeOfEnderSignal {

    public CraftEyeOfEnderSignal() {
        super(new CraftItemStack(Material.ENDER_EYE));
    }
    
    @Override
    public EntityType getType() {
        return EntityType.ENDER_SIGNAL;
    }
}
