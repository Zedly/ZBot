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
public class CraftDrowned extends CraftZombie implements Drowned {
    
    @Override
    public EntityType getType() {
        return EntityType.DROWNED;
    }
    
}
