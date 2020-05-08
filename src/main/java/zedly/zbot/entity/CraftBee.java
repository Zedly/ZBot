/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftBee extends CraftAnimal implements Bee {

    private boolean angry = false;
    private boolean hasStung = false;
    private boolean hasNectar = false;
    private int angerTime = 0;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(16)) {
            int flags = metaMap.get(16).asInt();
            angry = (flags & 0x02) != 0;
            hasStung = (flags & 0x04) != 0;
            hasNectar = (flags & 0x08) != 0;
        }
        if (metaMap.containsKey(17))
            angerTime = metaMap.get(17).asInt();
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.BEE;
    }

    @Override
    public boolean isAngry() {
        return angry;
    }

    @Override
    public boolean hasStung() {
        return hasStung;
    }

    @Override
    public boolean hasNectar() {
        return hasNectar;
    }

    @Override
    public int getAngerTicks() {
        return angerTime;
    }

}
