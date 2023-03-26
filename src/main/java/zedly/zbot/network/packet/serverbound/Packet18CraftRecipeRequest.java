package    zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* A replacement for <a href="/Protocol#Prepare_Crafting_Grid" title="Protocol">Prepare Crafting Grid</a>.  It appears to behave more or less the same, but the client does not specify where to move the items.
*/


/**
* This packet is sent when a player clicks a recipe in the crafting book that is craftable (white border).
*/


/**
* This packet is sent when a player clicks a recipe in the crafting book that is craftable (white border).
*/


/**
* This packet is sent when a player clicks a recipe in the crafting book that is craftable (white border).
*/

public class Packet18CraftRecipeRequest implements ServerBoundPacket {
    private final int windowID;
    private final String recipe;  // A recipe ID.
    private final boolean makeall;  // Affects the amount of items processed; true if shift is down when clicked.


    public Packet18CraftRecipeRequest(int windowID, String recipe, boolean makeall) {
        this.windowID = windowID;
        this.recipe = recipe;
        this.makeall = makeall;
    }

    @Override
    public int opCode() {
        return 0x18;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(windowID);
        dos.writeString(recipe);
        dos.writeBoolean(makeall);
    }
}
