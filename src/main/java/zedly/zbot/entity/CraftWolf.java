/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.DyeColor;
import zedly.zbot.entity.Wolf;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftWolf extends CraftTameable implements Wolf {

    protected float damageTaken = 0;
    protected boolean begging = false;
    protected DyeColor collarColor = DyeColor.RED;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            damageTaken = metaMap.get(15).asFloat();
        }
        if (metaMap.containsKey(16)) {
            begging = metaMap.get(16).asBoolean();
        }
        if (metaMap.containsKey(17)) {
            collarColor = DyeColor.values()[metaMap.get(17).asInt()];
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.WOLF;
    }

    @Override
    public synchronized float getDamageTaken() {
        return damageTaken;
    }

    @Override
    public synchronized boolean isBegging() {
        return begging;
    }

    @Override
    public synchronized DyeColor getCollarColor() {
        return collarColor;
    }

}
