/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;

/**
 *
 * @author Dennis
 */
public class CraftSkeletonHorse extends CraftAbstractHorse implements SkeletonHorse {

    @Override
    public EntityType getType() {
        return EntityType.SKELETON_HORSE;
    }
}
