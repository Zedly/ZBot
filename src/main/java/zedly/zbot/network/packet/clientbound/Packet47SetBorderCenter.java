package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* */

public class Packet47SetBorderCenter implements ClientBoundPacket {
    private double x;
    private double z;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        x = dis.readDouble();
        z = dis.readDouble();
    }

}
