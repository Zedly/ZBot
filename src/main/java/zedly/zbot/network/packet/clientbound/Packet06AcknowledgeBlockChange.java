package     zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.event.block.BlockChangeEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import zedly.zbot.network.mappings.BlockDataIds;


/**
* Fired whenever a block is changed within the render distance.
*/


/**
* Acknowledges a user-initiated block change. After receiving this packet, the client will display the block state sent by the server instead of the one predicted by the client.
*/

public class Packet06AcknowledgeBlockChange implements ClientBoundPacket {
    private int sequenceID;  // Represents the sequence to acknowledge, this is used for properly syncing block changes to the client after interactions.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        sequenceID = dis.readVarInt();
    }

    @Override
    public void process(GameContext context) {
        context.getSelf().getEnvironment().setBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ(), blockID);
        context.getMainThread().fireEvent(new BlockChangeEvent(location, BlockDataIds.fromId(blockID)));    }

}
