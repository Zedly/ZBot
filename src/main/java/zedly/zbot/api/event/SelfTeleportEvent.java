package zedly.zbot.api.event;

import zedly.zbot.Location;

public class SelfTeleportEvent extends Event {

    private final Location loc;
    private final int teleportId;

    public SelfTeleportEvent(Location loc, int teleportId) {
        this.loc = loc;
        this.teleportId = teleportId;
    }

    public Location getNewLocation() {
        return loc;
    }

    public int getTeleportId() {
        return teleportId;
    }
}
