package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;

/**
 *
 * @author Dennis
 */
/**
 * Used to send a chat message to the server. The message may not be longer than
 * 256 characters or else the server will kick the client.
 */
public class Packet05ChatMessage implements ServerBoundPacket {

    private final String message;  // The client sends the raw input, not a <a href="/Chat" title="Chat">Chat</a> component

    public Packet05ChatMessage(String message) {
        this.message = message;
    }

    @Override
    public int opCode() {
        return 0x05;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeString(message);
        dos.writeLong(System.currentTimeMillis());
        dos.writeLong(0x4206942069420690L);
        dos.writeVarInt(0);
        // no signed arguments
        dos.writeVarInt(0);
        dos.writeVarInt(0); // num acknowledgements, BitSet
        // no acknowledgements}
    }
}
