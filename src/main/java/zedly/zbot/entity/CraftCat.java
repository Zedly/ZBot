/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.DyeColor;
import zedly.zbot.EntityType;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftCat extends CraftTameable implements Cat {
    
    int typeId;
    int collarColor;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(17)) {
            typeId = metaMap.get(17).asInt();
        }
        if (metaMap.containsKey(20)) {
            collarColor = metaMap.get(20).asInt();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.CAT;
    }

    @Override
    public int getCatType() {
        return typeId;
    }

    @Override
    public DyeColor getCollarColor() {
        return DyeColor.values()[collarColor];
    }
    
}
