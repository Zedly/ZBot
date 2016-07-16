/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.api.entity.LeashKnot;

/**
 *
 * @author Dennis
 */
public class CraftLeashKnot extends CraftObject implements LeashKnot {

    @Override
    public EntityType getType() {
        return EntityType.LEASH_KNOT;
    }
    
}
