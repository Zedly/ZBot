/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.entity.Animal;
import zedly.zbot.event.Event;
import zedly.zbot.event.entity.EntityInLoveEvent;

/**
 *
 * @author Dennis
 */
public abstract class CraftAnimal extends CraftAgeable implements Animal {

    public synchronized Event setStatus(int status) {
        if (status == 18) {
            return new EntityInLoveEvent(this);
        } else {
            return null;
        }
    }

}
