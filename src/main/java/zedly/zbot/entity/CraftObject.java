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
public abstract class CraftObject extends CraftEntity implements zedly.zbot.entity.Object {

    protected int objectData;
    protected float[] velocity = new float[3];

    public synchronized void setVelocity(float[] velocity) {
        this.velocity = velocity;
    }

    public synchronized void setObjectData(int objectData) {
        this.objectData = objectData;
    }

    public synchronized float[] getVelocity() {
        return velocity;
    }

}
