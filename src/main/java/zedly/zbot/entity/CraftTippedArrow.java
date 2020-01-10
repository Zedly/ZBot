/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import zedly.zbot.EntityType;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftTippedArrow extends CraftArrow implements TippedArrow {

    protected int colorId = -1;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(10)) {
            colorId = metaMap.get(10).asInt();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.ARROW;
    }

    @Override
    public int getColor() {
        return colorId;
    }

}
