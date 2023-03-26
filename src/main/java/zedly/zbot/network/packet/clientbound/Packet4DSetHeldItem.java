package     zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */


/**
* Sent to change the player's slot selection.
*/


/**
* Sent to change the player's slot selection.
*/

public class Packet4DSetHeldItem implements ClientBoundPacket {
    private int slot;  // The slot which the player has selected (0–8).


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        slot = dis.readByte();
    }

}
