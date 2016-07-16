/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.api.event.entity;

import zedly.zbot.api.entity.Entity;
import zedly.zbot.api.event.Event;
import zedly.zbot.entity.EntityMeta;

/**
 *
 * @author Dennis
 */
public class EntityBaseEvent extends EntityMetaEvent {
    
    private final int newFlags;
    
    public EntityBaseEvent(Entity entity, EntityMeta meta) {
        super(entity);
        this.newFlags = meta.asInt();
    }
    
    public boolean isOnFire() {
        return (newFlags & 0x01) != 0;
    }
    
    public boolean isSneaking() {
        return (newFlags & 0x02) != 0;
    }
    
    public boolean isSprinting() {
        return (newFlags & 0x08) != 0;
    }
    
    public boolean isEating() {
        return (newFlags & 0x10) != 0;
    }
    
    public boolean isInvisible() {
        return (newFlags & 0x20) != 0;
    }
    
    public boolean isGlowing() {
        return (newFlags & 0x40) != 0;
    }
    
    public boolean isGliding() {
        return (newFlags & 0x80) != 0;
    }
    
}
