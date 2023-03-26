package     zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* Used to send a chat message to the server.  The message may not be longer than 256 characters or else the server will kick the client.
*/


/**
* Sent by the client to indicate that it has performed certain actions: sneaking (crouching), sprinting, exiting a bed, jumping with a horse, and opening a horse's inventory while riding it.
*/

public class Packet1EPlayerCommand implements ServerBoundPacket {
    private final int entityID;  // Player ID
    private final int actionID;  // The ID of the action, see below.
    private final int jumpBoost;  // Only used by the “start jump with horse” action, in which case it ranges from 0 to 100. In all other cases it is 0.


    public Packet1EPlayerCommand(int entityID, int actionID, int jumpBoost) {
        this.entityID = entityID;
        this.actionID = actionID;
        this.jumpBoost = jumpBoost;
    }

    @Override
    public int opCode() {
        return 0x1E;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(entityID);
        dos.writeVarInt(actionID);
        dos.writeVarInt(jumpBoost);
    }
}
