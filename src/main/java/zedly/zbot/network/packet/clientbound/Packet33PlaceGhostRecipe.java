package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
/**
* Response to the serverbound packet (<a href="#Place_Recipe">Place Recipe</a>), with the same recipe ID. Appears to be used to notify the UI.
*/

public class Packet33PlaceGhostRecipe implements ClientBoundPacket {
    private int windowID;
    private String recipe;  // A recipe ID.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readByte();
        recipe = dis.readString();
    }

}
