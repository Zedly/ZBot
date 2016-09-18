/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Boat;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftBoat extends CraftObject implements Boat {

    protected int timeSinceLastHit = 0;
    protected int forwardDirection = 0;
    protected float damageTaken = 0;
    protected int type = 0;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(6)) {
            timeSinceLastHit = metaMap.get(6).asInt();
        }
        if (metaMap.containsKey(7)) {
            forwardDirection = metaMap.get(7).asInt();
        }
        if (metaMap.containsKey(8)) {
            damageTaken = metaMap.get(8).asFloat();
        }
        if (metaMap.containsKey(9)) {
            type = metaMap.get(9).asInt();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.BOAT;
    }

    @Override
    public synchronized int getTimeSinceLastHit() {
        return timeSinceLastHit;
    }

    @Override
    public synchronized int getForwardDirection() {
        return forwardDirection;
    }

    @Override
    public synchronized float getDamageTaken() {
        return damageTaken;
    }

    @Override
    public synchronized int getTypeId() {
        return type;
    }

}
