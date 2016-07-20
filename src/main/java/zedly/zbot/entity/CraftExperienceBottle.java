/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.entity.ExperienceBottle;

/**
 *
 * @author Dennis
 */
public class CraftExperienceBottle extends CraftProjectile implements ExperienceBottle {

    @Override
    public EntityType getType() {
        return EntityType.EXPERIENCE_BOTTLE;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }
}
