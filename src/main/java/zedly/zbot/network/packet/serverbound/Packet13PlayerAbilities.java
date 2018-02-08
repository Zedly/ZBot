package  zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* The latter 2 bytes are used to indicate the walking and flying speeds respectively, while the first byte is used to determine the value of 4 booleans.
*/

public class Packet13PlayerAbilities implements ServerBoundPacket {
    private final int flags;  // Bit mask. 0x08: damage disabled (god mode), 0x04: can fly, 0x02: is flying, 0x01: is Creative
    private final double flyingSpeed;
    private final double walkingSpeed;


    public Packet13PlayerAbilities(int flags, double flyingSpeed, double walkingSpeed) {
        this.flags = flags;
        this.flyingSpeed = flyingSpeed;
        this.walkingSpeed = walkingSpeed;
    }

    @Override
    public int opCode() {
        return 0x13;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(flags);
        dos.writeFloat(flyingSpeed);
        dos.writeFloat(walkingSpeed);
    }
}
