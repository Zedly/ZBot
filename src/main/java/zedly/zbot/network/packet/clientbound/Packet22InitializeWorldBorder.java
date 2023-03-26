package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* */

public class Packet22InitializeWorldBorder implements ClientBoundPacket {
    private double x;
    private double z;
    private double oldDiameter;  // Current length of a single side of the world border, in meters.
    private double newDiameter;  // Target length of a single side of the world border, in meters.
    private long speed;  // Number of real-time <i>milli</i>seconds until New Diameter is reached. It appears that Notchian server does not sync world border speed to game ticks, so it gets out of sync with server lag. If the world border is not moving, this is set to 0.
    private int portalTeleportBoundary;  // Resulting coordinates from a portal teleport are limited to ±value. Usually 29999984.
    private int warningBlocks;  // In meters.
    private int warningTime;  // In seconds as set by <code>/worldborder warning time</code>.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        x = dis.readDouble();
        z = dis.readDouble();
        oldDiameter = dis.readDouble();
        newDiameter = dis.readDouble();
        speed = dis.readVarLong();
        portalTeleportBoundary = dis.readVarInt();
        warningBlocks = dis.readVarInt();
        warningTime = dis.readVarInt();
    }

}
