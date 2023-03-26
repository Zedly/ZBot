package  zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.SlotUpdateEvent;
import zedly.zbot.inventory.ItemStack;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * @author Dennis
 */
/**
 * Sent by the server when an item in a slot (in a window) is added/removed.
 */

/**
* Sent by the server when an item in a slot (in a window) is added/removed.
*/

public class Packet14SetContainerSlot implements ClientBoundPacket {
    private int windowID;  // The window which is being updated. 0 for player inventory. Note that all known window types include the player inventory. This packet will only be sent for the currently opened window while the player is performing actions, even if it affects the player inventory. After the window is closed, a number of these packets are sent to update the player's inventory window (0).
    private int stateID;  // The last received State ID from either a <a href="#Set_Container_Slot">Set Container Slot</a> or a <a href="#Set_Container_Content">Set Container Content</a> packet
    private int slot;  // The slot that should be updated.
    private ItemStack slotData;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readByte();
        stateID = dis.readVarInt();
        slot = dis.readShort();
        slotData = dis.readSlot();
    }

    @Override
    public void process(GameContext context) {
        context.getSelf().getInventory().setSlot(slot, slotData);
        context.getMainThread().fireEvent(new SlotUpdateEvent(slot, slotData));    }

}
