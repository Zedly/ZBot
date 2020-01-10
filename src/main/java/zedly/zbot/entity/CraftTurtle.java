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
public class CraftTurtle extends CraftAnimal implements Turtle {

    Location homePos, travelPos;
    boolean hasEgg, layingEgg, goingHome, travelling;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            homePos = metaMap.get(15).asLocation();
        }
        if (metaMap.containsKey(16)) {
            hasEgg = metaMap.get(16).asBoolean();
        }
        if (metaMap.containsKey(17)) {
            layingEgg = metaMap.get(17).asBoolean();
        }
        if (metaMap.containsKey(18)) {
            travelPos = metaMap.get(18).asLocation();
        }
        if (metaMap.containsKey(19)) {
            goingHome = metaMap.get(19).asBoolean();
        }
        if (metaMap.containsKey(20)) {
            travelling = metaMap.get(20).asBoolean();
        }
        return list;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.TURTLE;
    }

    @Override
    public Location getHomeLocation() {
        return homePos;
    }

    @Override
    public boolean hasEgg() {
        return hasEgg;
    }

    @Override
    public boolean isLayingEgg() {
        return layingEgg;
    }

    @Override
    public Location getTravelLocation() {
        return travelPos;
    }

    @Override
    public boolean isGoingHome() {
        return goingHome;
    }

    @Override
    public boolean isTravelling() {
        return travelling;
    }
    
}
