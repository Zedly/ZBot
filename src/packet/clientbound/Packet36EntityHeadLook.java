package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Changes the direction an entity's head is facing.
*/

public class Packet36EntityHeadLook implements ClientBoundPacket {
    private int entityID;
    private int headYaw;  // New angle, not a delta


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        headYaw = dis.readUnsignedByte();
    }

}
