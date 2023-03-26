package    zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
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
    private final int lastStateId;  // The ID of the window which was clicked. 0 for player inventory.
    private final int slot;  // The clicked slot number, see below
    private final int button;  // The button used in the click, see below
    private final int actionNumber;  // A unique number for the action, implemented by Notchian as a counter, starting at 1 (different counter for every window ID). Used by the server to send back a <a href="#Confirm_Transaction_.28clientbound.29">Confirm Transaction (clientbound)</a>.
    private final int mode;  // Inventory operation mode, see below
    private final List<Pair<Integer, ItemStack>> expectedNewStates;
    private final ItemStack clickeditem;  // The clicked slot. Has to be empty (item ID = -1) for drop mode.


    public Packet0BClickContainer(int windowID, int lastStateId, int slot, int button, int actionNumber, int mode, List<Pair<Integer, ItemStack>> expectedNewStates, ItemStack clickeditem) {
        this.windowID = windowID;
        this.lastStateId = lastStateId;
        this.slot = slot;
        this.button = button;
        this.actionNumber = actionNumber;
        this.mode = mode;
        this.expectedNewStates = expectedNewStates;
        this.clickeditem = clickeditem;
    }

    @Override
    public int opCode() {
        return 0x0B;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(windowID);
        dos.writeVarInt(lastStateId);
        dos.writeShort(slot);
        dos.writeByte(button);
        dos.writeVarInt(mode);
        
        dos.writeVarInt(expectedNewStates.size());
        for(Pair<Integer, ItemStack> slot : expectedNewStates) {
            dos.writeVarInt(slot.getLeft());
            dos.writeSlot(slot.getRight());
        }
        dos.writeSlot(clickeditem);
    }
}