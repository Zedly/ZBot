/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot;

import zedly.zbot.util.CartesianVector;
import zedly.zbot.util.Vector;

/**
 * @author Dennis
 */
public class Location {

    private double x, y, z, yaw, pitch;
    private boolean onGround = true;

    public Location() {
    }

    public Location(double x, double y, double z, double yaw, double pitch, boolean onGround) {
        this(x, y, z, yaw, pitch);
        this.onGround = onGround;
    }

    public Location(double x, double y, double z, double yaw, double pitch) {
        this(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(long l) {
        x = l >> 38;
        y = (l >> 26) & 0xFFF;
        z = l << 38 >> 38;
    }

    public double getPitchTo(Location l2) {
        double distance = distanceTo(l2);
        if (distance == 0) {
            return 0;
        } else {
            return Math.atan((l2.getY() - getY()) / distance);
        }
    }

    public double distanceTo(Location l2) {
        return Math.sqrt(distanceSquareTo(l2));
    }

    public double distanceSquareTo(Location l2) {
        return (l2.getX() - x) * (l2.getX() - x)
                + (l2.getY() - y) * (l2.getY() - y)
                + (l2.getZ() - z) * (l2.getZ() - z);
    }

    public Location getRelative(double x, double y, double z) {
        return getRelative(x, y, z, 0, 0);
    }

    public Location getRelative(double x, double y, double z, double yaw, double pitch) {
        return new Location(this.x + x, this.y + y, this.z + z, this.yaw + yaw, this.pitch + pitch);
    }

    public Location getRelative(Vector v) {
        Vector w = v.toCartesian();
        return new Location(this.x + w.getX(), this.y + w.getY(), this.z + w.getZ(), this.yaw, this.pitch);
    }

    public CartesianVector vectorTo(Location l2) {
        return new CartesianVector(l2.getX() - x, l2.getY() - y, l2.getZ() - z);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public int getBlockX() {
        return (int) Math.floor(x);
    }

    public int getBlockY() {
        return (int) Math.floor(y);
    }

    public int getBlockZ() {
        return (int) Math.floor(z);
    }

    public double getYaw() {
        return yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public boolean onGround() {
        return onGround;
    }

    public long toLong() {
        return ((long) getBlockX() & 0x3FFFFFF) << 38 | (((long) getBlockY() & 0xFFF) << 26) | ((long) getBlockZ() & 0x3FFFFFF);
    }
    
    public Location center() {
        return new Location(getBlockX() + 0.5, getBlockY() + 0.5, getBlockZ() + 0.5, getYaw(), getPitch());
    }
    
    public Location centerHorizontally() {
        return new Location(getBlockX() + 0.5, getBlockY(), getBlockZ() + 0.5, getYaw(), getPitch());        
    }

    @Override
    public Location clone() {
        return new Location(this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public String toString() {
        return "{" + x + ", " + y + ", " + z + " : " + yaw + "," + pitch + "}";
    }

}
