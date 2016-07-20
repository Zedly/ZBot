/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.MagmaCube;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftMagmaCube extends CraftInsentient implements MagmaCube {

    int size;

    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(12)) {
            size = metaMap.get(12).asInt();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.MAGMA_CUBE;
    }

    @Override
    public int getSize() {
        return size;
    }
    
}
