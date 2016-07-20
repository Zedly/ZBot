/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Arrow;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftArrow extends CraftProjectile implements Arrow {

    protected boolean critical;

    @Override
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(6)) {
            critical = metaMap.get(6).asInt() != 0;
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.ARROW;
    }

    @Override
    public boolean isCritical() {
        return critical;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }

}
