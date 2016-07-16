/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.api.block.Material;
import zedly.zbot.api.entity.DragonEgg;

/**
 *
 * @author Dennis
 */
public class CraftDragonEgg extends CraftObject implements DragonEgg {

    @Override
    public EntityType getType() {
        return EntityType.DRAGON_EGG;
    }

    @Override
    public Material getBlockType() {
        return Material.DRAGON_EGG;
    }

    @Override
    public int getBlockData() {
        return 0;
    }
    
}
