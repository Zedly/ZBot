/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.api.entity.Fireball;

/**
 *
 * @author Dennis
 */
public class CraftFireball extends CraftProjectile implements Fireball {

    @Override
    public EntityType getType() {
        return EntityType.FIREBALL;
    }

    @Override
    public boolean hasGravity() {
        return false;
    }
    
}
