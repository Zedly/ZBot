package  zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;


/**
* Sent when recipe is first seen in recipe book. Replaces Recipe Book Data, type 0.
*/

public class Packet22SetSeenRecipe implements ServerBoundPacket {
    private final String recipeID;


    public Packet22SetSeenRecipe(String recipeID) {
        this.recipeID = recipeID;
    }

    @Override
    public int opCode() {
        return 0x22;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeString(recipeID);
    }
}
