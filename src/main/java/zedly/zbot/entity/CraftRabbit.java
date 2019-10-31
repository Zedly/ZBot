/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Rabbit;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftRabbit extends CraftAnimal implements Rabbit {

    protected int rabbitType = 0;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            rabbitType = metaMap.get(15).asInt();
        }
        return list;
    }

    public EntityType getType() {
        return EntityType.RABBIT;
    }

    @Override
    public synchronized int getRabbitType() {
        return rabbitType;
    }

}
