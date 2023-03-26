package     zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.BlockFace;
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

public class Packet1DPlayerAction implements ServerBoundPacket {
    private final int status;  // The action the player is taking against the block (see below).
    private final Location location;  // Block position.
    private final int face;  // The face being hit (see below).
    private final int sequence;

    public Packet1DPlayerAction(Action status, Location location, int face, int sequence) {
        this.status = status.ordinal();
        this.location = location;
        this.face = face;
        this.sequence = sequence;
    }

    public Packet1DPlayerAction(int status, Location location, int face, int sequence) {
        this.status = status;
        this.location = location;
        this.face = face;
        this.sequence = sequence;
    }

    @Override
    public int opCode() {
        return 0x1D;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(status);
        dos.writePosition(location);
        dos.writeByte(face);
        dos.writeVarInt(sequence);
    }
    
    public static enum Action {
        START_DIGGING,
        CANCEL_DIGGING,
        FINISH_DIGGING,
        DROP_ITEM_STACK,
        DROP_ITEM,
        SHOOT_ARROW,
        SWAP_ITEMS
    }
}
