/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.api.entity.WitherSkull;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftWitherSkull extends CraftFireball implements WitherSkull {

    protected boolean invulnerable = false;

    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(6)) {
            invulnerable = metaMap.get(6).asBoolean();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.WITHER_SKULL;
    }

    @Override
    public boolean isInvulnerable() {
        return invulnerable;
    }

}
