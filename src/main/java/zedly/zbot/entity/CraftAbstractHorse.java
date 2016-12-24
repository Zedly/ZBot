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
public class CraftAbstractHorse extends CraftAnimal implements AbstractHorse {

    protected boolean tame = false, saddled = false, bred = false, chest = false, rearing = false,
            mouthOpen = false;
    UUID owner = null;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(13)) {
            int d = metaMap.get(13).asInt();
            tame = (d & 0x02) != 0;
            saddled = (d & 0x04) != 0;
            chest = (d & 0x08) != 0;
            bred = (d & 0x10) != 0;
            eating = (d & 0x20) != 0;
            rearing = (d & 0x40) != 0;
            mouthOpen = (d & 0x80) != 0;
        }
        if (metaMap.containsKey(14)) {
            owner = metaMap.get(14).asUUID();
        }
        return list;
    }

    public EntityType getType() {
        return EntityType.UNKNOWN;
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
    public synchronized boolean hasChest() {
        return chest;
    }

    @Override
    public synchronized boolean isBred() {
        return bred;
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

}
