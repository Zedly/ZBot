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
public class EntityNameChangeEvent extends EntityMetaEvent {
    
    private final String displayName;
    
    public EntityNameChangeEvent(Entity entity, EntityMeta meta) {
        super(entity);
        this.displayName = meta.asString();
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
}
