package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
/**
* Response to the serverbound packet (<a href="#Craft_Recipe_Request">Craft Recipe Request</a>), with the same recipe ID.  Appears to be used to notify the UI.
*/

public class Packet2BCraftRecipeResponse implements ClientBoundPacket {
    private int windowID;
    private int recipe;  // A recipe ID


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readByte();
        recipe = dis.readVarInt();
    }

}
