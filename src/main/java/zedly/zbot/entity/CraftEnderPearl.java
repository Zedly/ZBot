/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.entity.EnderPearl;

/**
 *
 * @author Dennis
 */
public class CraftEnderPearl extends CraftProjectile implements EnderPearl {
    
    @Override
    public EntityType getType() {
        return EntityType.ENDERPEARL;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }
    
}
