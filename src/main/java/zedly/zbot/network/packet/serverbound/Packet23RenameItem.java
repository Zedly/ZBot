package   zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;
/**
* Sent as a player is renaming an item in an anvil (each keypress in the anvil UI sends a new Name Item packet).  If the new name is empty, then the item loses its custom name (this is different from setting the custom name to the normal name of the item).  The item name may be no longer than 35 characters long, and if it is longer than that, then the rename is silently ignored.
*/


/**
* Sent as a player is renaming an item in an anvil (each keypress in the anvil UI sends a new Rename Item packet). If the new name is empty, then the item loses its custom name (this is different from setting the custom name to the normal name of the item). The item name may be no longer than 50 characters long, and if it is longer than that, then the rename is silently ignored.
*/

public class Packet23RenameItem implements ServerBoundPacket {
    private final String itemname;  // The new name of the item.


    public Packet23RenameItem(String itemname) {
        this.itemname = itemname;
    }

    @Override
    public int opCode() {
        return 0x23;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeString(itemname);
    }
}
