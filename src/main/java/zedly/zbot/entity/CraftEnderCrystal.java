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
import zedly.zbot.entity.EnderCrystal;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftEnderCrystal extends CraftObject implements EnderCrystal {
    
    protected Location beamTarget;
    boolean bottomVisible = true;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(7)) {
            beamTarget = metaMap.get(7).asLocation();
        }
        if (metaMap.containsKey(8)) {
            bottomVisible = metaMap.get(8).asBoolean();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.END_CRYSTAL;
    }

    @Override
    public synchronized Location getBeamTarget() {
        return beamTarget;
    }

    @Override
    public synchronized boolean hasBeamTarget() {
        return beamTarget != null;
    }

    @Override
    public boolean isBottomVisible() {
        return bottomVisible;
    }
    
}
