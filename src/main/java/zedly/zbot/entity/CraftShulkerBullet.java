/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.entity.ShulkerBullet;

/**
 *
 * @author Dennis
 */
public class CraftShulkerBullet extends CraftProjectile implements ShulkerBullet {
    
    @Override
    public EntityType getType() {
        return EntityType.SHULKER_BULLET;
    }
}
