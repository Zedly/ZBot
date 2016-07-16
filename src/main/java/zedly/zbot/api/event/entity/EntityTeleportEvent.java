/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.api.event.entity;

import zedly.zbot.Location;
import zedly.zbot.entity.CraftEntity;

/**
 *
 * @author Dennis
 */
public class EntityTeleportEvent extends EntityMoveEvent {
    
    public EntityTeleportEvent(CraftEntity ent, Location oldLoc, Location newLoc) {
        super(ent, oldLoc, newLoc);
    }
    
    @Override
    public boolean isTeleport() {
        return true;
    }
    
}
