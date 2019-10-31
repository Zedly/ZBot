/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Pig;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftPig extends CraftAnimal implements Pig {

    int carrotBoostTime = 0;
    protected boolean saddle = false;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            saddle = metaMap.get(15).asBoolean();
        }
        if (metaMap.containsKey(16)) {
            saddle = metaMap.get(16).asBoolean();
        }
        return list;
    }

    public EntityType getType() {
        return EntityType.PIG;
    }

    @Override
    public synchronized boolean hasSaddle() {
        return saddle;
    }

    @Override
    public int getCarrotBoostTime() {
        return carrotBoostTime;
    }
}
