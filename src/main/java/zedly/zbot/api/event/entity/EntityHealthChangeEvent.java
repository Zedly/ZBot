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
public class EntityHealthChangeEvent extends Event {
    
    private final Entity entity;
    private final float oldHealth;
    private final float newHealth;
    
    public EntityHealthChangeEvent(Entity entity, float oldHealth, float newHealth) {
        this.entity = entity;
        this.oldHealth = oldHealth;
        this.newHealth = newHealth;
    }

    public Entity getEntity() {
        return entity;
    }

    public float getOldHealth() {
        return oldHealth;
    }

    public float getNewHealth() {
        return newHealth;
    }
}
