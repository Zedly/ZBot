package     zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* Updates the direction the player is looking in.
*/


/**
* Updates the direction the player is looking in.
*/

public class Packet13PlayerRotation implements ServerBoundPacket {
    private final double yaw;  // Absolute rotation on the X Axis, in degrees.
    private final double pitch;  // Absolute rotation on the Y Axis, in degrees.
    private final boolean onGround;  // True if the client is on the ground, false otherwise.


    public Packet13PlayerRotation(double yaw, double pitch, boolean onGround) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public int opCode() {
        return 0x13;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeFloat(yaw);
        dos.writeFloat(pitch);
        dos.writeBoolean(onGround);
    }
}
