package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.packet.clientbound.ClientBoundPacket;

// Status, Clientbound


/**
* */

public class Packet00StatusResponse implements ClientBoundPacket {
    private int jSONLength;
    private String jSONResponse;  // See <a href="/Server_List_Ping#Response" title="Server List Ping">Server List Ping#Response</a>


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        jSONLength = dis.readVarInt();
        jSONResponse = dis.readString();
    }

}
Refactored ancestor. Review data strcuture