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
    
    @Override
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(6)) {
            beamTarget = metaMap.get(6).asLocation();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.ENDER_CRYSTAL;
    }

    @Override
    public Location getBeamTarget() {
        return beamTarget;
    }

    @Override
    public boolean hasBeamTarget() {
        return beamTarget != null;
    }
    
}
