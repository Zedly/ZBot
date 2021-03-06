/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public abstract class CraftChestedHorse extends CraftAbstractHorse implements ChestedHorse {

    boolean hasChest;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(17)) {
            hasChest = metaMap.get(17).asBoolean();
        }
        return list;
    }

    @Override
    public boolean hasChest() {
        return hasChest;
    }
}
