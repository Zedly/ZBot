/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.WoolColor;
import zedly.zbot.api.entity.ArrowTipped;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftArrowTipped extends CraftArrow implements ArrowTipped {

    private WoolColor color = WoolColor.WHITE;

    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(7)) {
            color = WoolColor.values()[metaMap.get(7).asInt()];
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.TIPPED_ARROW;
    }

    @Override
    public WoolColor getColor() {
        return color;
    }

}
