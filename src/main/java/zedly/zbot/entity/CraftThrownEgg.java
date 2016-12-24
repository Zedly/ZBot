/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.entity.ThrownEgg;

/**
 *
 * @author Dennis
 */
public class CraftThrownEgg extends CraftProjectile implements ThrownEgg {

    @Override
    public EntityType getType() {
        return EntityType.THROWN_EGG;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }
    
}
