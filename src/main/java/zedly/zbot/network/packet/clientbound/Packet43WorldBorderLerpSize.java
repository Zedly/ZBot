package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* */

public class Packet43WorldBorderLerpSize implements ClientBoundPacket {
    private double oldDiameter;  // Current length of a single side of the world border, in meters.
    private double newDiameter;  // Target length of a single side of the world border, in meters.
    private long speed;  // Number of real-time <i>milli</i>seconds until New Diameter is reached. It appears that Notchian server does not sync world border speed to game ticks, so it gets out of sync with server lag. If the world border is not moving, this is set to 0.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        oldDiameter = dis.readDouble();
        newDiameter = dis.readDouble();
        speed = dis.readVarLong();
    }

}
