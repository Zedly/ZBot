package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Applies a cooldown period to all items with the given type.  Used by the Notchian server with enderpearls.  This packet should be sent when the cooldown starts and also when the cooldown ends (to compensate for lag), although the client will end the cooldown automatically.
*/

public class Packet17SetCooldown implements ClientBoundPacket {
    private int itemID;  // Numeric ID of the item to apply a cooldown to.
    private int cooldownTicks;  // Number of ticks to apply a cooldown for, or 0 to clear the cooldown.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        itemID = dis.readVarInt();
        cooldownTicks = dis.readVarInt();
    }

}
