/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import java.util.HashMap;
import java.util.List;
import zedly.zbot.entity.Unknown;
import zedly.zbot.event.Event;

/**
 * Fallback class for unimplemented entities
 * @author Dennis
 */
public class CraftUnknown extends CraftEntity implements Unknown {
    
    protected int typeId;
    
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        //do additional stuff
        return list;
    }
    
    public void setEntityTypeId(int id) {
        this.typeId = id;
    }
    
    @Override
    public int getEntityTypeId() {
        return typeId;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.UNKNOWN;
    }
    
}
