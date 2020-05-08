/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.Material;
import zedly.zbot.EntityType;
import zedly.zbot.entity.ExperienceBottle;
import zedly.zbot.inventory.CraftItemStack;

/**
 *
 * @author Dennis
 */
public class CraftThrownExperienceBottle extends CraftItemedThrowable implements ExperienceBottle {

    public CraftThrownExperienceBottle() {
        super(new CraftItemStack(Material.EXPERIENCE_BOTTLE));
    }
    
    @Override
    public EntityType getType() {
        return EntityType.EXPERIENCE_BOTTLE;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }
}
