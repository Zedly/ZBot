/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.api.event.entity;

import zedly.zbot.api.entity.Entity;
import zedly.zbot.api.event.Event;

/**
 * @author Dennis
 */
public class EntitySpawnEvent extends Event {

    private final Entity ent;

    public EntitySpawnEvent(Entity ent) {
        this.ent = ent;
    }

    public Entity getEntity() {
        return ent;
    }
}
