/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Bat;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftBat extends CraftAmbient implements Bat {

    protected boolean hanging;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(14)) {
            hanging = metaMap.get(14).asInt() != 0;
        }
        return list;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.BAT;
    }

    @Override
    public synchronized boolean isHanging() {
        return hanging;
    }

}
