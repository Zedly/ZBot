/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.api.event.entity;

import zedly.zbot.api.entity.Entity;
import zedly.zbot.entity.EntityMeta;

/**
 *
 * @author Dennis
 */
public class EntityAirChangeEvent extends EntityMetaEvent {
    
    private final int newAir;
    
    public EntityAirChangeEvent(Entity entity, EntityMeta meta) {
        super(entity);
        this.newAir = meta.asInt();
    }
    
    public int getAir() {
        return newAir;
    }
    
}
