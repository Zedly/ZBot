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
* */

public class Packet1FPlayerBlockPlacement implements ServerBoundPacket {
    private final Location location;  // Block position
    private final int face;  // The face on which the block is placed (as documented at <a href="#Player_Digging">Player Digging</a>)
    private final int hand;  // The hand from which the block is placed; 0: main hand, 1: off hand
    private final double cursorPositionX;  // The position of the crosshair on the block, from 0 to 1 increasing from west to east
    private final double cursorPositionY;  // The position of the crosshair on the block, from 0 to 1 increasing from bottom to top
    private final double cursorPositionZ;  // The position of the crosshair on the block, from 0 to 1 increasing from north to south


    public Packet1FPlayerBlockPlacement(Location location, int face, int hand, double cursorPositionX, double cursorPositionY, double cursorPositionZ) {
        this.location = location;
        this.face = face;
        this.hand = hand;
        this.cursorPositionX = cursorPositionX;
        this.cursorPositionY = cursorPositionY;
        this.cursorPositionZ = cursorPositionZ;
    }

    @Override
    public int opCode() {
        return 0x1F;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writePosition(location);
        dos.writeVarInt(face);
        dos.writeVarInt(hand);
        dos.writeFloat(cursorPositionX);
        dos.writeFloat(cursorPositionY);
        dos.writeFloat(cursorPositionZ);
    }
}
