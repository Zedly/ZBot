/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.Location;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftDolphin extends CraftWaterMob implements Dolphin {
    
    Location treasureLocation;
    boolean canFindTreasure;
    boolean hasFish;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            treasureLocation = metaMap.get(15).asLocation();
        }
        if (metaMap.containsKey(16)) {
            canFindTreasure = metaMap.get(16).asBoolean();
        }
        if (metaMap.containsKey(17)) {
            hasFish = metaMap.get(17).asBoolean();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.DOLPHIN;
    }

    @Override
    public Location getTreasurePosition() {
        return treasureLocation;
    }

    @Override
    public boolean canFindTreasure() {
        return canFindTreasure;
    }

    @Override
    public boolean hasFish() {
        return hasFish;
    }
    
}
