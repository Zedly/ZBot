package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* */

public class Packet45WorldBorderWarningDelay implements ClientBoundPacket {
    private int warningTime;  // In seconds as set by <code>/worldborder warning time</code>.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        warningTime = dis.readVarInt();
    }

}
