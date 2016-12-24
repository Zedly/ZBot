/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.entity.EyeOfEnderSignal;

/**
 *
 * @author Dennis
 */
public class CraftEyeOfEnderSignal extends CraftProjectile implements EyeOfEnderSignal {

    @Override
    public EntityType getType() {
        return EntityType.EYE_OF_ENDER;
    }

    @Override
    public boolean hasGravity() {
        return false;
    }
    
}
