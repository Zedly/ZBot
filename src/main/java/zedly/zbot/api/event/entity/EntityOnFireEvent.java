/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.api.event.entity;

import zedly.zbot.api.entity.Entity;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class EntityOnFireEvent extends Event {
    
    private final Entity entity;
    private final boolean onFire;
    
    public EntityOnFireEvent(Entity entity, boolean onFire) {
        this.entity = entity;
        this.onFire = onFire;
    }
    
    public Entity getEntity() {
        return entity;
    }
    
    public boolean onFire() {
        return onFire;
    }
    
}
