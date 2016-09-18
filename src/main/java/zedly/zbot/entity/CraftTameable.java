/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import zedly.zbot.entity.Tameable;
import zedly.zbot.event.Event;
import zedly.zbot.event.entity.EntityTameEvent;

/**
 *
 * @author Dennis
 */
public abstract class CraftTameable extends CraftAnimal implements Tameable {

    protected boolean sitting = false, angry = false, tamed = false;
    protected UUID owner;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(13)) {
            int d = metaMap.get(13).asInt();
            sitting = (d & 0x01) != 0;
            angry = (d & 0x02) != 0;
            tamed = (d & 0x04) != 0;
            if(tamed) {
                list.add(new EntityTameEvent(this));
            }
        }
        if (metaMap.containsKey(14)) {
            owner = metaMap.get(14).asUUID();
        }
        return list;
    }

    @Override
    public synchronized boolean isSittng() {
        return sitting;
    }

    @Override
    public synchronized boolean isAngry() {
        return angry;
    }

    @Override
    public synchronized boolean isTamed() {
        return tamed;
    }

    @Override
    public synchronized boolean hasOwner() {
        return owner != null;
    }

    @Override
    public synchronized UUID getOwner() {
        return owner;
    }

}
