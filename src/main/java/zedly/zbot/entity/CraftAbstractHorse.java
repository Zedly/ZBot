/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import zedly.zbot.EntityType;
import zedly.zbot.event.Event;
import zedly.zbot.entity.AbstractHorse;

/**
 *
 * @author Dennis
 */
public abstract class CraftAbstractHorse extends CraftAnimal implements AbstractHorse {

    protected boolean tame = false, saddled = false, bred = false, eating = false, rearing = false,
            mouthOpen = false;
    UUID owner = null;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            int d = metaMap.get(15).asInt();
            tame = (d & 0x02) != 0;
            saddled = (d & 0x04) != 0;
            bred = (d & 0x08) != 0;
            eating = (d & 0x10) != 0;
            rearing = (d & 0x20) != 0;
            mouthOpen = (d & 0x40) != 0;
        }
        if (metaMap.containsKey(16)) {
            owner = metaMap.get(16).asUUID();
        }
        return list;
    }

    @Override
    public synchronized boolean isTame() {
        return tame;
    }

    @Override
    public synchronized boolean isSaddled() {
        return saddled;
    }

    @Override
    public synchronized boolean hasBred() {
        return bred;
    }

    @Override
    public synchronized boolean isEating() {
        return eating;
    }

    @Override
    public synchronized boolean isRearing() {
        return rearing;
    }

    @Override
    public synchronized boolean isMouthOpen() {
        return mouthOpen;
    }

    @Override
    public synchronized boolean hasOwner() {
        return owner != null;
    }

    @Override
    public synchronized UUID getOwner() {
        return owner;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }

}
