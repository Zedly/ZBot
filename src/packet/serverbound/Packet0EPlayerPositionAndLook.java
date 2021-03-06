package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataOutputStream;

/**
 *
 * @author Dennis
 */
/**
 * A combination of <a href="#Player_Look">Player Look</a> and
 * <a href="#Player_Position">Player Position</a>.
 */
public class Packet0EPlayerPositionAndLook implements ServerBoundPacket {

    private final double x;  // Absolute position
    private final double feetY;  // Absolute feet position, normally Head Y - 1.62
    private final double z;  // Absolute position
    private final double yaw;  // Absolute rotation on the X Axis, in degrees
    private final double pitch;  // Absolute rotation on the Y Axis, in degrees
    private final boolean onGround;  // True if the client is on the ground, false otherwise

    public Packet0EPlayerPositionAndLook(double x, double feetY, double z, double yaw, double pitch, boolean onGround) {
        this.x = x;
        this.feetY = feetY;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public Packet0EPlayerPositionAndLook(Location location, boolean onGround) {
        this(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), onGround);
    }

    @Override
    public int opCode() {
        return 0x0E;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeDouble(x);
        dos.writeDouble(feetY);
        dos.writeDouble(z);
        dos.writeFloat(yaw);
        dos.writeFloat(pitch);
        dos.writeBoolean(onGround);
    }
}
