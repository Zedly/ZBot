package     zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* Sent when a player moves in a vehicle. Fields are the same as in <a href="#Player_Position_And_Look_.28serverbound.29">Player Position And Look</a>. Note that all fields use absolute positioning and do not allow for relative positioning.
*/


/**
* Sent when a player moves in a vehicle. Fields are the same as in <a href="#Set_Player_Position_and_Rotation">Set Player Position and Rotation</a>. Note that all fields use absolute positioning and do not allow for relative positioning.
*/

public class Packet18MoveVehicle implements ServerBoundPacket {
    private final double x;  // Absolute position (X coordinate).
    private final double y;  // Absolute position (Y coordinate).
    private final double z;  // Absolute position (Z coordinate).
    private final double yaw;  // Absolute rotation on the vertical axis, in degrees.
    private final double pitch;  // Absolute rotation on the horizontal axis, in degrees.


    public Packet18MoveVehicle(double x, double y, double z, double yaw, double pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public int opCode() {
        return 0x18;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeDouble(x);
        dos.writeDouble(y);
        dos.writeDouble(z);
        dos.writeFloat(yaw);
        dos.writeFloat(pitch);
    }
}
