/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.entity.SmallFireball;

/**
 *
 * @author Dennis
 */
public class CraftSmallFireball extends CraftProjectile implements SmallFireball {

    @Override
    public EntityType getType() {
        return EntityType.FIRE_CHARGE;
    }

    @Override
    public synchronized boolean hasGravity() {
        return true;
    }
    
}
