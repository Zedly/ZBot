package  zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* Updates the player's XYZ position on the server.
*/

public class Packet0DPlayerPosition implements ServerBoundPacket {
    private final double x;  // Absolute position
    private final double feetY;  // Absolute feet position, normally Head Y - 1.62
    private final double z;  // Absolute position
    private final boolean onGround;  // True if the client is on the ground, false otherwise


    public Packet0DPlayerPosition(double x, double feetY, double z, boolean onGround) {
        this.x = x;
        this.feetY = feetY;
        this.z = z;
        this.onGround = onGround;
    }

    @Override
    public int opCode() {
        return 0x0D;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeDouble(x);
        dos.writeDouble(feetY);
        dos.writeDouble(z);
        dos.writeBoolean(onGround);
    }
}
