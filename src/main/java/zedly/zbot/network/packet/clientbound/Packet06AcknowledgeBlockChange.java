package     zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.network.ExtendedDataInputStream;
import java.io.IOException;
import zedly.zbot.event.block.ActionAcknowledgedEvent;


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
        context.getMainThread().fireEvent(new ActionAcknowledgedEvent(sequenceID));
    }
}
