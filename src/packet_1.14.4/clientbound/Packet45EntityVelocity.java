package   zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Velocity is believed to be in units of 1/8000 of a block per server tick (50ms); for example, -1343 would move (-1343 / 8000) = −0.167875 blocks per tick (or −3,3575 blocks per second).
*/


/**
* Velocity is believed to be in units of 1/8000 of a block per server tick (50ms); for example, -1343 would move (-1343 / 8000) = −0.167875 blocks per tick (or −3,3575 blocks per second).
*/

public class Packet45EntityVelocity implements ClientBoundPacket {
    private int entityID;
    private int velocityX;  // Velocity on the X axis
    private int velocityY;  // Velocity on the Y axis
    private int velocityZ;  // Velocity on the Z axis


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        velocityX = dis.readShort();
        velocityY = dis.readShort();
        velocityZ = dis.readShort();
    }

}
