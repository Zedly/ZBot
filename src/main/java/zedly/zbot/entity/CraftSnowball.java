/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.api.entity.Snowball;

/**
 *
 * @author Dennis
 */
public class CraftSnowball extends CraftProjectile implements Snowball {

    @Override
    public EntityType getType() {
        return EntityType.SNOWBALL;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }
    
}
