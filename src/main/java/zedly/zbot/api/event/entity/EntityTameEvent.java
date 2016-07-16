/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.api.event.entity;

import zedly.zbot.api.entity.Tameable;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class EntityTameEvent extends Event {

    private final Tameable tame;

    public EntityTameEvent(Tameable tame) {
        this.tame = tame;
    }

    public Tameable getEntity() {
        return tame;
    }

}
