package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* */

public class Packet44WorldBorderSize implements ClientBoundPacket {
    private double diameter;  // Length of a single side of the world border, in meters.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        diameter = dis.readDouble();
    }

}
