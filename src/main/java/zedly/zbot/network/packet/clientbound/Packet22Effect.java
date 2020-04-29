package   zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Sent when a client is to play a sound or particle effect.
*/


/**
* Sent when a client is to play a sound or particle effect.
*/

public class Packet22Effect implements ClientBoundPacket {
    private int effectID;  // The ID of the effect, see below
    private Location location;  // The location of the effect
    private int data;  // Extra data for certain effects, see below
    private boolean disableRelativeVolume;  // See above


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        effectID = dis.readInt();
        location = dis.readPosition();
        data = dis.readInt();
        disableRelativeVolume = dis.readBoolean();
    }
    
}
