/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.api.entity.Bat;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftBat extends CraftAmbient implements Bat {

    protected boolean hanging;

    @Override
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(12)) {
            hanging = metaMap.get(12).asInt() != 0;
        }
        return list;
    }
    
    public EntityType getType() {
        return EntityType.BAT;
    }

    @Override
    public boolean isHanging() {
        return hanging;
    }

}
