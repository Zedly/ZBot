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
* */

public class Packet07ClientCommand implements ServerBoundPacket {
    private final int actionID;  // See below


    public Packet07ClientCommand(int actionID) {
        this.actionID = actionID;
    }

    @Override
    public int opCode() {
        return 0x07;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(actionID);
    }
}
