package     zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* */


/**
* */


/**
* */


/**
* */

public class Packet1FPlayerInput implements ServerBoundPacket {
    private final double sideways;  // Positive to the left of the player.
    private final double forward;  // Positive forward.
    private final int flags;  // Bit mask. 0x1: jump, 0x2: unmount.


    public Packet1FPlayerInput(double sideways, double forward, int flags) {
        this.sideways = sideways;
        this.forward = forward;
        this.flags = flags;
    }

    @Override
    public int opCode() {
        return 0x1F;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeFloat(sideways);
        dos.writeFloat(forward);
        dos.writeByte(flags);
    }
}
