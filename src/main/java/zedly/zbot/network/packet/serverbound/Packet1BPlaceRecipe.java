package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* This packet is sent when a player clicks a recipe in the crafting book that is craftable (white border).
*/

public class Packet1BPlaceRecipe implements ServerBoundPacket {
    private final int windowID;
    private final String recipe;  // A recipe ID.
    private final boolean makeall;  // Affects the amount of items processed; true if shift is down when clicked.


    public Packet1BPlaceRecipe(int windowID, String recipe, boolean makeall) {
        this.windowID = windowID;
        this.recipe = recipe;
        this.makeall = makeall;
    }

    @Override
    public int opCode() {
        return 0x1B;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(windowID);
        dos.writeString(recipe);
        dos.writeBoolean(makeall);
    }
}
