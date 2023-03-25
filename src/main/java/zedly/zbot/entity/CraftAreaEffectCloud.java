/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.AreaEffectCloud;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftAreaEffectCloud extends CraftObject implements AreaEffectCloud {

    protected double radius = 0.5;
    protected int colorId = 0;
    protected boolean ignoreRadius = false;
    protected String particleId = "effect";

    @Override
    public EntityType getType() {
        return EntityType.AREA_EFFECT_CLOUD;
    }

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(7)) {
            radius = metaMap.get(7).asFloat();
        }
        if (metaMap.containsKey(8)) {
            colorId = metaMap.get(8).asInt();
        }
        if (metaMap.containsKey(9)) {
            ignoreRadius = metaMap.get(9).asBoolean();
        }
        if (metaMap.containsKey(10)) {
            particleId = metaMap.get(10).asString();
        }
        return list;
    }

    @Override
    public synchronized double getRadius() {
        return radius;
    }

    @Override
    public synchronized int getColorId() {
        return colorId;
    }

    @Override
    public synchronized boolean isPoint() {
        return ignoreRadius;
    }

    @Override
    public synchronized String getParticleId() {
        return particleId;
    }

    @Override
    public boolean hasGravity() {
        return false;
    }

}
