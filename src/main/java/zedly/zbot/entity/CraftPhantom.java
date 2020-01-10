/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftPhantom extends CraftFlying implements Phantom {

    int size;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(14)) {
            size = metaMap.get(14).asInt();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.PHANTOM;
    }

    @Override
    public int getSize() {
        return size;
    }

}
