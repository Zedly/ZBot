package  zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* Sent when the player mines a block. A Notchian server only accepts digging packets with coordinates within a 6-unit radius between the center of the block and 1.5 units from the player's feet (<i>not</i> their eyes).
*/

public class Packet14PlayerDigging implements ServerBoundPacket {
    private final int status;  // The action the player is taking against the block (see below)
    private final Location location;  // Block position
    private final int face;  // The face being hit (see below)


    public Packet14PlayerDigging(int status, Location location, int face) {
        this.status = status;
        this.location = location;
        this.face = face;
    }

    @Override
    public int opCode() {
        return 0x14;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(status);
        dos.writePosition(location);
        dos.writeByte(face);
    }
}
