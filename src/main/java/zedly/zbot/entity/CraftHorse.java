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
import zedly.zbot.api.entity.Horse;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftHorse extends CraftAnimal implements Horse {

    protected boolean tame = false, saddled = false, bred = false, chest = false, rearing = false,
            mouthOpen = false;
    int variant = 0;
    int style = 0;
    UUID owner = null;
    int armor = 0;

    @Override
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
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
            variant = metaMap.get(14).asInt();
        }
        if (metaMap.containsKey(15)) {
            style = metaMap.get(15).asInt();
        }
        if (metaMap.containsKey(16)) {
            owner = metaMap.get(16).asUUID();
        }
        if (metaMap.containsKey(17)) {
            armor = metaMap.get(17).asInt();
        }
        return list;
    }

    public EntityType getType() {
        return EntityType.HORSE;
    }

    @Override
    public boolean isTame() {
        return tame;
    }

    @Override
    public boolean isSaddled() {
        return saddled;
    }

    @Override
    public boolean hasChest() {
        return chest;
    }

    @Override
    public boolean isBred() {
        return bred;
    }

    @Override
    public boolean isRearing() {
        return rearing;
    }

    @Override
    public boolean isMouthOpen() {
        return mouthOpen;
    }

    @Override
    public int getVariant() {
        return variant;
    }

    @Override
    public int getStyle() {
        return style;
    }

    @Override
    public boolean hasOwner() {
        return owner != null;
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public int getArmor() {
        return armor;
    }

}
