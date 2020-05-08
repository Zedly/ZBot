package    zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* */

public class Packet2CPlayerBlockPlacement implements ServerBoundPacket {
    private final int hand;  // The hand from which the block is placed; 0: main hand, 1: off hand
    private final Location location;  // Block position
    private final int face;  // The face on which the block is placed (as documented at <a href="#Player_Digging">Player Digging</a>)
    private final double cursorPositionX;  // The position of the crosshair on the block, from 0 to 1 increasing from west to east
    private final double cursorPositionY;  // The position of the crosshair on the block, from 0 to 1 increasing from bottom to top
    private final double cursorPositionZ;  // The position of the crosshair on the block, from 0 to 1 increasing from north to south
    private final boolean insideblock;  // True when the player's head is inside of a block.


    public Packet2CPlayerBlockPlacement(int hand, Location location, int face, double cursorPositionX, double cursorPositionY, double cursorPositionZ, boolean insideblock) {
        this.hand = hand;
        this.location = location;
        this.face = face;
        this.cursorPositionX = cursorPositionX;
        this.cursorPositionY = cursorPositionY;
        this.cursorPositionZ = cursorPositionZ;
        this.insideblock = insideblock;
    }

    @Override
    public int opCode() {
        return 0x2C;
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
    }
}
