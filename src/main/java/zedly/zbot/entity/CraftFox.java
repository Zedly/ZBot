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

/**
 *
 * @author Dennis
 */
public class CraftFox extends CraftAnimal implements Fox {

    int typeId;
    int flags;
    UUID firstUUID, secondUUID;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(16)) {
            typeId = metaMap.get(16).asInt();
        }
        if (metaMap.containsKey(17)) {
            flags = metaMap.get(17).asInt();
        }
        if (metaMap.containsKey(18)) {
            firstUUID = metaMap.get(18).asUUID();
        }
        if (metaMap.containsKey(19)) {
            firstUUID = metaMap.get(19).asUUID();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.FOX;
    }

    @Override
    public int getTypeId() {
        return typeId;
    }

    @Override
    public boolean isSitting() {
        return (flags & 0x01) != 0;
    }

    @Override
    public boolean isCrouching() {
        return (flags & 0x04) != 0;
    }

    @Override
    public boolean isSleeping() {
        return (flags & 0x20) != 0;
    }

    @Override
    public UUID getFirstUUID() {
        return firstUUID;
    }

    @Override
    public UUID getSecondUUID() {
        return secondUUID;
    }

}
