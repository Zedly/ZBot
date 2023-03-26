package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.entity.CraftEntityMeta.ParticleMeta;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Sent when a client is to play a sound or particle effect.
*/

public class Packet25WorldEvent implements ClientBoundPacket {
    private int event;  // The event, see below.
    private Location location;  // The location of the event.
    private int data;  // Extra data for certain events, see below.
    private boolean disableRelativeVolume;  // See above.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        event = dis.readInt();
        location = dis.readPosition();
        data = dis.readInt();
        disableRelativeVolume = dis.readBoolean();
    }

}
