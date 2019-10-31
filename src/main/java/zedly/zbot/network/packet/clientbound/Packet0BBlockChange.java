package   zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.event.block.BlockChangeEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;


/**
* Fired whenever a block is changed within the render distance.
*/


/**
* Fired whenever a block is changed within the render distance.
*/

public class Packet0BBlockChange implements ClientBoundPacket {
    private Location location;  // Block Coordinates
    private int blockID;  // The new block state ID for the block as given in the <span class="plainlinks"><a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Data_values%23Block_IDs">global palette</a></span>. See that section for more information.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        location = dis.readPosition();
        blockID = dis.readVarInt();
    }

    @Override
    public void process(GameContext context) {
        context.getSelf().getEnvironment().setBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ(), blockID >> 4, blockID & 0xF);
        context.getMainThread().fireEvent(new BlockChangeEvent(location, blockID));    }

}
