package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* This packet tells that a player goes to bed.
*/

public class Packet30UseBed implements ClientBoundPacket {
    private int entityID;  // Sleeping player's EID
    private Location location;  // Block location of the head part of the bed


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        location = dis.readPosition();
    }

}
