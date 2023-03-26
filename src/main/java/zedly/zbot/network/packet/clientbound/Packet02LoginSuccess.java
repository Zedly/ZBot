package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.UUID;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
/**
 *
 */

/**
* */

public class Packet02LoginSuccess implements ClientBoundPacket {
    private UUID uUID;
    private String username;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        uUID = dis.readUUID();
        username = dis.readString();
    }
}
