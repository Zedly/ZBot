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
    protected boolean leftPaddleTurning = false;
    protected boolean rightPaddleTurning = false;
    protected int splashTimer = 0;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(7)) {
            timeSinceLastHit = metaMap.get(7).asInt();
        }
        if (metaMap.containsKey(8)) {
            forwardDirection = metaMap.get(8).asInt();
        }
        if (metaMap.containsKey(9)) {
            damageTaken = metaMap.get(9).asFloat();
        }
        if (metaMap.containsKey(10)) {
            type = metaMap.get(10).asInt();
        }
        if (metaMap.containsKey(11)) {
            leftPaddleTurning = metaMap.get(11).asBoolean();
        }
        if (metaMap.containsKey(12)) {
            rightPaddleTurning = metaMap.get(12).asBoolean();
        }
        if (metaMap.containsKey(13)) {
            splashTimer = metaMap.get(13).asInt();
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

    @Override
    public boolean isLeftPaddleTurning() {
        return leftPaddleTurning;
    }

    @Override
    public boolean isRightPaddleTurning() {
        return rightPaddleTurning;
    }

    @Override
    public int getSplashTimer() {
        return splashTimer;
    }

}
