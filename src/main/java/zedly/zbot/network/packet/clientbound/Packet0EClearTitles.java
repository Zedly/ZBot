package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
/**
* Clear the client's current title information, with the option to also reset it.
*/

public class Packet0EClearTitles implements ClientBoundPacket {
    private boolean reset;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        reset = dis.readBoolean();
    }

}
