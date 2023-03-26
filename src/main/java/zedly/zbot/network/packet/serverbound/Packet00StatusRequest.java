package  zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;

//Login, Client


/**
* Sent by client as confirmation of <a href="#Synchronize_Player_Position">Synchronize Player Position</a>.
*/

public class Packet00StatusRequest implements ServerBoundPacket {
    public Packet00StatusRequest() {
    }

    @Override
    public int opCode() {
        return 0x00;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
    }
}
