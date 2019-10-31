package   zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Sent by the server after login to specify the coordinates of the spawn point (the point at which players spawn at, and which the compass points to). It can be sent at any time to update the point compasses point at.
*/


/**
* Sent by the server after login to specify the coordinates of the spawn point (the point at which players spawn at, and which the compass points to). It can be sent at any time to update the point compasses point at.
*/

public class Packet4DSpawnPosition implements ClientBoundPacket {
    private Location location;  // Spawn location


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        location = dis.readPosition();
    }

}
