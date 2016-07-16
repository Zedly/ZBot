/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.api.entity.Projectile;

/**
 *
 * @author Dennis
 */
public abstract class CraftProjectile extends CraftObject implements Projectile {

    @Override
    public int getFiringEntityId() {
        return objectData;
    }
    
}
