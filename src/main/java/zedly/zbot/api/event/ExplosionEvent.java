package zedly.zbot.api.event;

import zedly.zbot.Location;

public class ExplosionEvent extends Event {

    private final Location loc;
    private final float radius;
    private final byte[][] records;
    private final float motionX;
    private final float motionY;
    private final float motionZ;

    public ExplosionEvent(float x, float y, float z, float radius, byte[][] records, float motionX, float motionY, float motionZ) {
        loc = new Location(x, y, z);
        this.radius = radius;
        this.records = records;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }
    
    public Location getLocation() {
        return loc;
    }

    public float getRadius() {
        return radius;
    }

    public byte[][] getRecords() {
        return records;
    }

    public float getMotionX() {
        return motionX;
    }

    public float getMotionY() {
        return motionY;
    }

    public float getMotionZ() {
        return motionZ;
    }
}
