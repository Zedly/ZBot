package     zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Sets the entity that the player renders from. This is normally used when the player left-clicks an entity while in spectator mode.
*/


/**
* Sets the entity that the player renders from. This is normally used when the player left-clicks an entity while in spectator mode.
*/

public class Packet4CSetCamera implements ClientBoundPacket {
    private int cameraID;  // ID of the entity to set the client's camera to.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        cameraID = dis.readVarInt();
    }

}
