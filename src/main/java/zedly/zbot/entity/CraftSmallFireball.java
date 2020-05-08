/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.Material;
import zedly.zbot.EntityType;
import zedly.zbot.inventory.CraftItemStack;

/**
 *
 * @author Dennis
 */
public class CraftSmallFireball extends CraftItemedFireball implements SmallFireball {

    public CraftSmallFireball() {
        super(new CraftItemStack(Material.FIRE_CHARGE));
    }
    
    @Override
    public EntityType getType() {
        return EntityType.SMALL_FIREBALL;
    }
}
