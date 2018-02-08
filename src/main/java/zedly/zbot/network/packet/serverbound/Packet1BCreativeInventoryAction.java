package  zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.inventory.ItemStack;
import zedly.zbot.network.ExtendedDataOutputStream;

/**
 *
 * @author Dennis
 */

/**
* While the user is in the standard inventory (i.e., not a crafting bench) in Creative mode, the player will send this packet.
*/

public class Packet1BCreativeInventoryAction implements ServerBoundPacket {
    private final int slot;  // Inventory slot
    private final ItemStack clickedItem;


    public Packet1BCreativeInventoryAction(int slot, ItemStack clickedItem) {
        this.slot = slot;
        this.clickedItem = clickedItem;
    }

    @Override
    public int opCode() {
        return 0x1B;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeShort(slot);
        dos.writeSlot(clickedItem);
    }
}
