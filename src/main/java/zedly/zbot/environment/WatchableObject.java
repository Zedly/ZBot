package zedly.zbot.environment;

public class WatchableObject {

    private final int objectType;

    /**
     * id of max 31
     */
    private final int dataValueId;
    private Object watchedObject;
    private boolean watched;

    public WatchableObject(int par1, int par2, Object par3Obj) {
        this.dataValueId = par2;
        this.watchedObject = par3Obj;
        this.objectType = par1;
        this.watched = true;
    }

    public synchronized int getDataValueId() {
        return this.dataValueId;
    }

    public synchronized void setObject(Object par1Obj) {
        this.watchedObject = par1Obj;
    }

    public synchronized Object getObject() {
        return this.watchedObject;
    }

    public synchronized int getObjectType() {
        return this.objectType;
    }

    public synchronized boolean isWatched() {
        return this.watched;
    }

    public synchronized void setWatched(boolean par1) {
        this.watched = par1;
    }
}
