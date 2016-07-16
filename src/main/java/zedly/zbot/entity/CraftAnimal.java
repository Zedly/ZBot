/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.api.entity.Animal;
import zedly.zbot.api.event.Event;
import zedly.zbot.api.event.entity.EntityInLoveEvent;

/**
 *
 * @author Dennis
 */
public abstract class CraftAnimal extends CraftAgeable implements Animal {

    public Event setStatus(int status) {
        if (status == 18) {
            return new EntityInLoveEvent(this);
        } else {
            return null;
        }
    }

}
