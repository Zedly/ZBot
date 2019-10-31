package   zedly.zbot.network.packet.serverbound;

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
* Used to send a chat message to the server.  The message may not be longer than 256 characters or else the server will kick the client.
*/

public class Packet03ChatMessage implements ServerBoundPacket {
    private final String message;  // The client sends the raw input, not a <a href="/Chat" title="Chat">Chat</a> component


    public Packet03ChatMessage(String message) {
        this.message = message;
    }

    @Override
    public int opCode() {
        return 0x03;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeString(message);
    }
}
