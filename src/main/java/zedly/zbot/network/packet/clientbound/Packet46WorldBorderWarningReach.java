package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* */

public class Packet46WorldBorderWarningReach implements ClientBoundPacket {
    private int warningBlocks;  // In meters.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        warningBlocks = dis.readVarInt();
    }

}
