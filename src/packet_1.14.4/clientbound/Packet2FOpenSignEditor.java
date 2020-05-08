package   zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Sent when the client has placed a sign and is allowed to send <a href="#Update_Sign">Update Sign</a>.  There must already be a sign at the given location (which the client does not do automatically) - send a <a href="#Block_Change">Block Change</a> first.
*/


/**
* Sent when the client has placed a sign and is allowed to send <a href="#Update_Sign">Update Sign</a>.  There must already be a sign at the given location (which the client does not do automatically) - send a <a href="#Block_Change">Block Change</a> first.
*/

public class Packet2FOpenSignEditor implements ClientBoundPacket {
    private Location location;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        location = dis.readPosition();
    }

}
