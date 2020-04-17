/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.WitherSkull;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftWitherSkull extends CraftAbstractFireball implements WitherSkull {

    protected boolean invulnerable = false;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(7)) {
            invulnerable = metaMap.get(7).asBoolean();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.WITHER_SKULL;
    }

    @Override
    public synchronized boolean isInvulnerable() {
        return invulnerable;
    }

}
