/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import java.util.HashMap;
import java.util.List;
import zedly.zbot.WoolColor;
import zedly.zbot.entity.Sheep;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftSheep extends CraftAnimal implements Sheep {

    protected boolean sheared = false;
    protected WoolColor color = WoolColor.WHITE;

    @Override
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(13)) {
            int d = metaMap.get(13).asInt();
            sheared = (d & 0x10) != 0;
            color = WoolColor.values()[d & 0xF];
        }
        return list;
    }

    @Override
    public WoolColor getColor() {
        return color;
    }

    @Override
    public boolean isSheared() {
        return sheared;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.SHEEP;
    }

}
