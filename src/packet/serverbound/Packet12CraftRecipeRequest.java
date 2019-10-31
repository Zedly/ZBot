package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* A replacement for <a href="/Protocol#Prepare_Crafting_Grid" title="Protocol">Prepare Crafting Grid</a>.  It appears to behave more or less the same, but the client does not specify where to move the items.
*/

public class Packet12CraftRecipeRequest implements ServerBoundPacket {
    private final int windowID;
    private final int recipe;  // A recipe ID
    private final boolean makeall;  // Affects the amount of items processed; true if shift is down when clicked


    public Packet12CraftRecipeRequest(int windowID, int recipe, boolean makeall) {
        this.windowID = windowID;
        this.recipe = recipe;
        this.makeall = makeall;
    }

    @Override
    public int opCode() {
        return 0x12;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(windowID);
        dos.writeVarInt(recipe);
        dos.writeBoolean(makeall);
    }
}
