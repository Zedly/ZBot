package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.packet.clientbound.ClientBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* */

public class Packet02LoginSuccess implements ClientBoundPacket {
    private String uUID;  // Unlike in other packets, this field contains the UUID as a string with hyphens.
    private String username;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        uUID = dis.readString();
        username = dis.readString();
    }

}
Refactored ancestor. Review data strcuture