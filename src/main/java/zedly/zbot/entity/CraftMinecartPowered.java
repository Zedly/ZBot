/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.MinecartPowered;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftMinecartPowered extends CraftMinecart implements MinecartPowered {

    protected boolean powered;
    
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(12)) {
            powered = metaMap.get(12).asBoolean();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.MINECART;
    }
    
    @Override
    public synchronized boolean isPowered() {
        return powered;
    }
    
}
