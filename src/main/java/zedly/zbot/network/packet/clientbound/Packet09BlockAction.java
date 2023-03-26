package     zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.event.block.BlockActionEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
* This packet is used for a number of actions and animations performed by blocks, usually non-persistent.
*/


/**
* This packet is used for a number of actions and animations performed by blocks, usually non-persistent.
*/


/**
* This packet is used for a number of actions and animations performed by blocks, usually non-persistent.  The client ignores the provided block type and instead uses the block state in their world.
*/

public class Packet09BlockAction implements ClientBoundPacket {
    private Location location;  // Block coordinates.
    private int actionId;  // Varies depending on block — see <a href="/Block_Actions" title="Block Actions">Block Actions</a>.
    private int actionParameter;  // Varies depending on block — see <a href="/Block_Actions" title="Block Actions">Block Actions</a>.
    private int blockType;  // The block type ID for the block.  This must match the block at the given coordinates.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        location = dis.readPosition();
        actionId = dis.readUnsignedByte();
        actionParameter = dis.readUnsignedByte();
        blockType = dis.readVarInt();
    }

    @Override
    public void process(GameContext context) {
        context.getMainThread().fireEvent(new BlockActionEvent(location, blockType, actionId, actionParameter));
    }

}
