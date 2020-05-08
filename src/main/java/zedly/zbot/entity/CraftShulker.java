/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.Direction;
import zedly.zbot.DyeColor;
import zedly.zbot.EntityType;
import zedly.zbot.Location;
import zedly.zbot.entity.Shulker;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftShulker extends CraftGolem implements Shulker {

    protected Direction direction;
    protected Location attachedLocation;
    protected int shieldHeight;
    protected int colorId;
    
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            direction = metaMap.get(15).asDirection();
        }
        if (metaMap.containsKey(16)) {
            attachedLocation = metaMap.get(16).asLocation();
        }
        if (metaMap.containsKey(17)) {
            shieldHeight = metaMap.get(17).asInt();
        }
        if (metaMap.containsKey(18)) {
            colorId = metaMap.get(18).asInt();
        }
        return list;
    }
    
    public EntityType getType() {
        return EntityType.SHULKER;
    }

    @Override
    public synchronized Direction getDirection() {
        return direction;
    }

    @Override
    public synchronized Location getAttachedLocation() {
        return attachedLocation;
    }

    @Override
    public synchronized int getShieldHeight() {
        return shieldHeight;
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.values()[colorId];
    }
    
}
