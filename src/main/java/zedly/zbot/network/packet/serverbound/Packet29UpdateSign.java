package   zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataOutputStream;

/**
 *
 * @author Dennis
 */

/**
* <span id="Update_Sign_.28serverbound.29"></span>This message is sent from the client to the server when the “Done” button is pushed after placing a sign.
*/


/**
* <span id="Update_Sign_.28serverbound.29"></span>This message is sent from the client to the server when the “Done” button is pushed after placing a sign.
*/

public class Packet29UpdateSign implements ServerBoundPacket {
    private final Location location;  // Block Coordinates
    private final String line1;  // First line of text in the sign
    private final String line2;  // Second line of text in the sign
    private final String line3;  // Third line of text in the sign
    private final String line4;  // Fourth line of text in the sign


    public Packet29UpdateSign(Location location, String line1, String line2, String line3, String line4) {
        this.location = location;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.line4 = line4;
    }

    @Override
    public int opCode() {
        return 0x29;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writePosition(location);
        dos.writeString(line1);
        dos.writeString(line2);
        dos.writeString(line3);
        dos.writeString(line4);
    }
}
