package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* 0–9 are the displayable destroy stages and each other number means that there is no animation on this coordinate.
*/

public class Packet08BlockBreakAnimation implements ClientBoundPacket {
    private int entityID;  // Entity ID of the entity breaking the block
    private Location location;  // Block Position
    private int destroyStage;  // 0–9 to set it, any other value to remove it


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        location = dis.readPosition();
        destroyStage = dis.readByte();
    }

}
