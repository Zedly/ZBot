package zedly.zbot.api.event.block;

import zedly.zbot.Location;
import zedly.zbot.api.event.Event;

public class BlockActionEvent extends Event {

    private Location loc;
    private int blockType;

    public BlockActionEvent(Location loc, int blockType) {
        this.loc = loc;
        this.blockType = blockType;
    }

    public Location getLocation() {
        return loc;
    }

    public int getBlockType() {
        return blockType;
    }
}
