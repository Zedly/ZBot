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

public class Packet03MessageAcknowledgment implements ServerBoundPacket {
    private final int messageCount;


    public Packet03MessageAcknowledgment(int messageCount) {
        this.messageCount = messageCount;
    }

    @Override
    public int opCode() {
        return 0x03;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(messageCount);
    }
}
