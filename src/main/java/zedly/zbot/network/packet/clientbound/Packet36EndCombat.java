package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Unused by the Notchian client.  This data was once used for twitch.tv metadata circa 1.8.
*/

public class Packet36EndCombat implements ClientBoundPacket {
    private int duration;  // Length of the combat in ticks.
    private int entityID;  // ID of the primary opponent of the ended combat, or -1 if there is no obvious primary opponent.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        duration = dis.readVarInt();
        entityID = dis.readInt();
    }

}
