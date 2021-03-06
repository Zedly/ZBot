/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.entity.Unknown;

/**
 *
 * @author Dennis
 */
public class CraftUnknownObject extends CraftObject implements Unknown {

    protected int typeId;

    @Override
    public EntityType getType() {
        return EntityType.UNKNOWN;
    }

    public synchronized void setEntityTypeId(int id) {
        this.typeId = id;
    }

    @Override
    public synchronized int getEntityTypeId() {
        return typeId;
    }

}
