package     zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* Sent when pressing the Use Item key (default: right click) with an item in hand.
*/


/**
* */

public class Packet31UseItemOn implements ServerBoundPacket {
    private final int hand;  // The hand from which the block is placed; 0: main hand, 1: off hand.
    private final Location location;  // Block position.
    private final int face;  // The face on which the block is placed (as documented at <a href="#Player_Action">Player Action</a>).
    private final double cursorPositionX;  // The position of the crosshair on the block, from 0 to 1 increasing from west to east.
    private final double cursorPositionY;  // The position of the crosshair on the block, from 0 to 1 increasing from bottom to top.
    private final double cursorPositionZ;  // The position of the crosshair on the block, from 0 to 1 increasing from north to south.
    private final boolean insideblock;  // True when the player's head is inside of a block.
    private final int sequence;


    public Packet31UseItemOn(int hand, Location location, int face, double cursorPositionX, double cursorPositionY, double cursorPositionZ, boolean insideblock, int sequence) {
        this.hand = hand;
        this.location = location;
        this.face = face;
        this.cursorPositionX = cursorPositionX;
        this.cursorPositionY = cursorPositionY;
        this.cursorPositionZ = cursorPositionZ;
        this.insideblock = insideblock;
        this.sequence = sequence;
    }

    @Override
    public int opCode() {
        return 0x31;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(hand);
        dos.writePosition(location);
        dos.writeVarInt(face);
        dos.writeFloat(cursorPositionX);
        dos.writeFloat(cursorPositionY);
        dos.writeFloat(cursorPositionZ);
        dos.writeBoolean(insideblock);
        dos.writeVarInt(sequence);
    }
}
