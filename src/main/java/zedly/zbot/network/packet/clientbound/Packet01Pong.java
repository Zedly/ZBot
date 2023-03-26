package   zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.clientbound.ClientBoundPacket;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* */


/**
* */

public class Packet01Pong implements ClientBoundPacket {
    private long payload;  // Should be the same as sent by the client.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        payload = dis.readLong();
    }

}
