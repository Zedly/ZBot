/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.api.event.entity;

import zedly.zbot.api.entity.Blaze;
import zedly.zbot.api.event.Event;
import zedly.zbot.entity.CraftBlaze;

/**
 *
 * @author Dennis
 */
public class BlazeChargeEvent extends Event {

    private final CraftBlaze blaze;

    public BlazeChargeEvent(CraftBlaze blaze) {
        this.blaze = blaze;
    }

    public Blaze getEntity() {
        return blaze;
    }

}
