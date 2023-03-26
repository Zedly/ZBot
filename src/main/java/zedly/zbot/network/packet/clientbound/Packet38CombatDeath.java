package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Used to send a respawn screen.
*/

public class Packet38CombatDeath implements ClientBoundPacket {
    private int playerID;  // Entity ID of the player that died (should match the client's entity ID).
    private int entityID;  // The killer entity's ID, or -1 if there is no obvious killer.
    private String message;  // The death message.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        playerID = dis.readVarInt();
        entityID = dis.readInt();
        message = dis.readString();
    }

}
