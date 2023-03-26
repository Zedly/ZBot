package    zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.inventory.ItemStack;
import zedly.zbot.network.ExtendedDataOutputStream;

/**
 *
 * @author Dennis
 */

/**
* This packet is sent by the player when it clicks on a slot in a window.
*/

public class Packet0BClickContainer implements ServerBoundPacket {
    private final int windowID;  // The ID of the window which was clicked. 0 for player inventory.
    private final int slot;  // The clicked slot number, see below
    private final int button;  // The button used in the click, see below
    private final int actionNumber;  // A unique number for the action, implemented by Notchian as a counter, starting at 1 (different counter for every window ID). Used by the server to send back a <a href="#Confirm_Transaction_.28clientbound.29">Confirm Transaction (clientbound)</a>.
    private final int mode;  // Inventory operation mode, see below
    private final ItemStack clickeditem;  // The clicked slot. Has to be empty (item ID = -1) for drop mode.


    public Packet0BClickContainer(int windowID, int slot, int button, int actionNumber, int mode, ItemStack clickeditem) {
        this.windowID = windowID;
        this.slot = slot;
        this.button = button;
        this.actionNumber = actionNumber;
        this.mode = mode;
        this.clickeditem = clickeditem;
    }

    @Override
    public int opCode() {
        return 0x0B;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(windowID);
        dos.writeShort(slot);
        dos.writeByte(button);
        dos.writeShort(actionNumber);
        dos.writeVarInt(mode);
        dos.writeSlot(clickeditem);
    }
}
Refactored ancestor. Review data strcuture