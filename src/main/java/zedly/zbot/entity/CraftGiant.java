/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.api.entity.Giant;

/**
 *
 * @author Dennis
 */
public class CraftGiant extends CraftMonster implements Giant {

    @Override
    public EntityType getType() {
        return EntityType.GIANT_ZOMBIE;
    }

}
