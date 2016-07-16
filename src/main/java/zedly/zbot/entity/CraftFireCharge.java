/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.api.entity.FireCharge;

/**
 *
 * @author Dennis
 */
public class CraftFireCharge extends CraftProjectile implements FireCharge {

    @Override
    public EntityType getType() {
        return EntityType.FIRE_CHARGE;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }
    
}
