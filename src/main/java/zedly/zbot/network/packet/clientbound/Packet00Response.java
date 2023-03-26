package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

// Status, Clientbound


/**
* */

public class Packet00Response implements ClientBoundPacket {
    private String jSONResponse;  // See <a href="/Server_List_Ping#Response" title="Server List Ping">Server List Ping#Response</a>


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        jSONResponse = dis.readString();
    }

}