/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.api.event.entity;

import zedly.zbot.api.entity.Ghast;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class GhastChargeEvent extends Event {
    
    private final Ghast ghast;

    public GhastChargeEvent(Ghast ghast) {
        this.ghast = ghast;
    }

    public Ghast getEntity() {
        return ghast;
    }
    
}
