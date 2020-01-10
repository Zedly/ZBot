package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataInputStream;
/**
* */

public class Packet5CAcknowledgePlayerDigging implements ClientBoundPacket {
    private Location location;  // Position where the digging was happening
    private int block;  // Block state ID of the block that should be at that position now.
    private int status;  // Same as Player Digging.  Only Started digging (0), Cancelled digging (1), and Finished digging (2) are used.
    private boolean successful;  // True if the digging succeeded; false if the client should undo any changes it made locally.  (How does this work?)


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        location = dis.readPosition();
        block = dis.readVarInt();
        status = dis.readVarInt();
        successful = dis.readBoolean();
    }

}
