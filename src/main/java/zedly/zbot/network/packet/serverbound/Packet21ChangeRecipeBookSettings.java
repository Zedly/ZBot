package  zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;


/**
* Replaces Recipe Book Data, type 1.
*/

public class Packet21ChangeRecipeBookSettings implements ServerBoundPacket {
    private final int bookID;  // 0: crafting, 1: furnace, 2: blast furnace, 3: smoker.
    private final boolean bookOpen;
    private final boolean filterActive;


    public Packet21ChangeRecipeBookSettings(int bookID, boolean bookOpen, boolean filterActive) {
        this.bookID = bookID;
        this.bookOpen = bookOpen;
        this.filterActive = filterActive;
    }

    @Override
    public int opCode() {
        return 0x21;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(bookID);
        dos.writeBoolean(bookOpen);
        dos.writeBoolean(filterActive);
    }
}
