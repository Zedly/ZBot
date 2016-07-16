/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.Direction;
import zedly.zbot.EntityType;
import zedly.zbot.Location;
import zedly.zbot.api.entity.Shulker;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftShulker extends CraftGolem implements Shulker {

    protected Direction direction;
    protected Location attachedLocation;
    protected int shieldHeight;
    
    
    @Override
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(12)) {
            direction = metaMap.get(12).asDirection();
        }
        if (metaMap.containsKey(13)) {
            attachedLocation = metaMap.get(13).asLocation();
        }
        if (metaMap.containsKey(14)) {
            shieldHeight = metaMap.get(14).asInt();
        }
        return list;
    }
    
    public EntityType getType() {
        return EntityType.SHULKER;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public Location getAttachedLocation() {
        return attachedLocation;
    }

    @Override
    public int getShieldHeight() {
        return shieldHeight;
    }
    
}
