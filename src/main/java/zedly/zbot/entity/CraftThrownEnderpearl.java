/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.entity.ThrownEnderPearl;

/**
 *
 * @author Dennis
 */
public class CraftThrownEnderpearl extends CraftItemedThrowable implements ThrownEnderPearl {
    
    @Override
    public EntityType getType() {
        return EntityType.ENDER_PEARL;
    }   
}
