/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

/**
 *
 * @author Dennis
 */
public abstract class CraftObject extends CraftEntity implements zedly.zbot.api.entity.Object {

    protected int objectData;
    protected float[] velocity = new float[3];

    public void setVelocity(float[] velocity) {
        this.velocity = velocity;
    }

    public void setObjectData(int objectData) {
        this.objectData = objectData;
    }

    public float[] getVelocity() {
        return velocity;
    }

}
