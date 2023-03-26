package   zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
/**
* Sent by the integrated singleplayer server when changing render distance.  Does not appear to be used by the dedicated server, as <code>view-distance</code> in server.properties cannot be changed at runtime.
*/


/**
* Sent by the integrated singleplayer server when changing render distance.  This packet is sent by the server when the client reappears in the overworld after leaving the end.
*/

public class Packet4AUpdateViewDistance implements ClientBoundPacket {
    private int viewDistance;  // Render distance (2-32).


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        viewDistance = dis.readVarInt();
    }

}
