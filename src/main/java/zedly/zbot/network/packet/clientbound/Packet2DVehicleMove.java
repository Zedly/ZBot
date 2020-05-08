package    zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Note that all fields use absolute positioning and do not allow for relative positioning.
*/

public class Packet2DVehicleMove implements ClientBoundPacket {
    private double x;  // Absolute position (X coordinate)
    private double y;  // Absolute position (Y coordinate)
    private double z;  // Absolute position (Z coordinate)
    private double yaw;  // Absolute rotation on the vertical axis, in degrees
    private double pitch;  // Absolute rotation on the horizontal axis, in degrees


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        yaw = dis.readFloat();
        pitch = dis.readFloat();
    }

}
