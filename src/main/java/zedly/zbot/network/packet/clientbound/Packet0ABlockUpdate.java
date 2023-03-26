package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.event.block.BlockChangeEvent;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.mappings.BlockDataIds;

/**
 *
 * @author Dennis
 */
/**
 * Fired whenever a block is changed within the render distance.
 */
public class Packet0ABlockUpdate implements ClientBoundPacket {

    private Location location;  // Block Coordinates.
    private int blockID;  // The new block state ID for the block as given in the <span class="plainlinks"><a rel="nofollow" class="external text" href="https://minecraft.fandom.com/wiki/Data_values%23Block_IDs">global palette</a></span>. See that section for more information.

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        location = dis.readPosition();
        blockID = dis.readVarInt();
    }

    @Override
    public void process(GameContext context) {
        context.getMainThread().fireEvent(new BlockChangeEvent(location, BlockDataIds.fromId(blockID)));
        context.getSelf().getEnvironment().setBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ(), blockID);
    }
}
