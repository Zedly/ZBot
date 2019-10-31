/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Enderdragon;
import zedly.zbot.event.Event;
import zedly.zbot.inventory.CraftItemStack;

/**
 *
 * @author Dennis
 */
public class CraftEnderdragon extends CraftInsentient implements Enderdragon {

    protected int phase = 0;

    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(14)) {
            phase = metaMap.get(14).asInt();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.ENDER_DRAGON;
    }

    @Override
    public int getPhase() {
        return phase;
    }

}
