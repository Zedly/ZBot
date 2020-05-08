/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Mooshroom;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftMooshroom extends CraftCow implements Mooshroom {

    String canonicalType = "red";

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(16)) {
            canonicalType = metaMap.get(16).asString();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.MOOSHROOM;
    }

    @Override
    public String getCanonicalType() {
        return canonicalType;
    }

}
