/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.api.event.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import zedly.zbot.api.entity.Entity;
import zedly.zbot.api.event.Event;
import zedly.zbot.entity.EntityMeta;

/**
 *
 * @author Dennis
 */
public class EntityMetadataEvent extends Event {
    
    private final Entity entity;
    private final Map<Integer, EntityMeta> metaMap;
    
    public EntityMetadataEvent(Entity entity, HashMap<Integer, EntityMeta> metaMap) {
        this.entity = entity;
        this.metaMap = Collections.unmodifiableMap(metaMap);
    }
    
    public Entity getEntity() {
        return entity;
    }
    
    public Map<Integer, EntityMeta> getMap() {
        return metaMap;
    }
    
}
