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
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftArrow extends CraftEntity implements Arrow {

    protected boolean critical;
    protected boolean noclip;
    protected UUID shooter;
    protected int piercingLevel;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(7)) {
            critical = (metaMap.get(7).asInt() & 0x1) != 0;
            noclip = (metaMap.get(7).asInt() & 0x2) != 0;
        }
        if (metaMap.containsKey(8)) {
            shooter = UUID.fromString(metaMap.get(8).asString());
        }
        if (metaMap.containsKey(9)) {
            piercingLevel = metaMap.get(9).asInt();
        }
        
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.ARROW;
    }

    @Override
    public synchronized boolean isCritical() {
        return critical;
    }

    @Override
    public synchronized boolean hasGravity() {
        return true;
    }

    @Override
    public UUID getShooter() {
        return shooter;
    }

    @Override
    public boolean isNoClip() {
        return noclip;
    }

    @Override
    public int getPiercingLevel() {
        return piercingLevel;
    }

}
