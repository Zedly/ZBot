package    zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.inventory.ItemStack;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* */


/**
* */


/**
* */

public class Packet0BEditBook implements ServerBoundPacket {
    private final int slot;  // The hotbar slot where the written book is located
    private final int count;  // Number of elements in the following array
    private final String entries;  // Text from each page.
    private final boolean hastitle;  // If true, the next field is present.
    private final String title;  // Title of book.


    public Packet0BEditBook(int slot, int count, String entries, boolean hastitle, String title) {
        this.slot = slot;
        this.count = count;
        this.entries = entries;
        this.hastitle = hastitle;
        this.title = title;
    }

    @Override
    public int opCode() {
        return 0x0B;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(slot);
        dos.writeVarInt(count);
        dos.writeString(entries);
        dos.writeBoolean(hastitle);
        dos.writeString(title);
    }
}
