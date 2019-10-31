/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.Material;
import zedly.zbot.entity.Snowball;
import zedly.zbot.inventory.CraftItemStack;
import zedly.zbot.inventory.ItemStack;

/**
 *
 * @author Dennis
 */
public class CraftSnowball extends CraftItemedThrowable implements Snowball {

    public CraftSnowball() {
        super(new CraftItemStack(Material.SNOWBALL));
    }
    
    @Override
    public EntityType getType() {
        return EntityType.SNOWBALL;
    }
    
}
