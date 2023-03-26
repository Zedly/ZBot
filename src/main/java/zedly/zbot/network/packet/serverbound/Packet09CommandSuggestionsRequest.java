package    zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataOutputStream;

/**
 *
 * @author Dennis
 */

/**
* Sent when the client needs to tab-complete a <code>minecraft:ask_server</code> suggestion type.
*/


/**
* Sent when the client needs to tab-complete a <code>minecraft:ask_server</code> suggestion type.
*/

public class Packet09CommandSuggestionsRequest implements ServerBoundPacket {
    private final int transactionId;  // The id of the transaction that the server will send back to the client in the response of this packet. Client generates this and increments it each time it sends another tab completion that doesn't get a response.
    private final String text;  // All text behind the cursor without the <code>/</code> (e.g. to the left of the cursor in left-to-right languages like English).


    public Packet09CommandSuggestionsRequest(int transactionId, String text) {
        this.transactionId = transactionId;
        this.text = text;
    }

    @Override
    public int opCode() {
        return 0x09;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(transactionId);
        dos.writeString(text);
    }
}