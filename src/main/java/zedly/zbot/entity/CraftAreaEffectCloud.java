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

    protected float radius = 0;
    protected int colorId = 0;
    protected boolean ignoreRadius = false;
    protected int particleId = 0;

    @Override
    public EntityType getType() {
        return EntityType.CLOUD;
    }

    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(6)) {
            radius = metaMap.get(6).asFloat();
        }
        if (metaMap.containsKey(7)) {
            colorId = metaMap.get(7).asInt();
        }
        if (metaMap.containsKey(8)) {
            ignoreRadius = metaMap.get(8).asBoolean();
        }
        if (metaMap.containsKey(9)) {
            particleId = metaMap.get(9).asInt();
        }
        return list;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public int getColorId() {
        return colorId;
    }

    @Override
    public boolean isPoint() {
        return ignoreRadius;
    }

    @Override
    public int getParticleId() {
        return particleId;
    }

}
