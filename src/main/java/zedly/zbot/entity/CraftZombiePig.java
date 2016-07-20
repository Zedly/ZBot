/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.entity.ZombiePig;

/**
 *
 * @author Dennis
 */
public class CraftZombiePig extends CraftMonster implements ZombiePig {

    @Override
    public EntityType getType() {
        return EntityType.ZOMBIE_PIGMAN;
    }
    
}
