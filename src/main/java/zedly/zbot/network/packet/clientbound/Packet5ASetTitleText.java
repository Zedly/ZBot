package   zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* */


/**
* */

public class Packet5ASetTitleText implements ClientBoundPacket {
    private String titleText;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        titleText = dis.readString();
    }

}
