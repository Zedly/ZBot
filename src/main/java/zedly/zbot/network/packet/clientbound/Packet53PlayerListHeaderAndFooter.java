package   zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* This packet may be used by custom servers to display additional information above/below the player list. It is never sent by the Notchian server.
*/


/**
* This packet may be used by custom servers to display additional information above/below the player list. It is never sent by the Notchian server.
*/

public class Packet53PlayerListHeaderAndFooter implements ClientBoundPacket {
    private String header;  // To remove the header, send a empty translatable component: {"translate":""}
    private String footer;  // To remove the footer, send a empty translatable component: {"translate":""}


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        header = dis.readString();
        footer = dis.readString();
    }

}
