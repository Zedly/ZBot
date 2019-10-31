/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import java.util.HashMap;
import java.util.List;
import zedly.zbot.DyeColor;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftSheep extends CraftAnimal implements Sheep {

    protected boolean sheared = false;
    protected DyeColor color = DyeColor.WHITE;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            int d = metaMap.get(15).asInt();
            sheared = (d & 0x10) != 0;
            color = DyeColor.values()[d & 0xF];
        }
        return list;
    }

    @Override
    public synchronized DyeColor getColor() {
        return color;
    }

    @Override
    public synchronized boolean isSheared() {
        return sheared;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.SHEEP;
    }
    
    public String toString() {
        return super.toString() + ", color: " + color + ", sheared: " + sheared;
    }

}
