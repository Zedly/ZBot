package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
/**
* */


/**
* */

public class Packet41ActionBar implements ClientBoundPacket {
    private String actionbartext;  // Displays a message above the hotbar (the same as position 2 in <a href="#Chat_Message_.28clientbound.29">Chat Message (clientbound)</a>.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        actionbartext = dis.readString();
    }

}
