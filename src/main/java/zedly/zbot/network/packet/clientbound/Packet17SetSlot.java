package zedly.zbot.network.packet.clientbound;

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
public class Packet17SetSlot implements ClientBoundPacket {

    private int windowID;  // The window which is being updated. 0 for player inventory. Note that all known window types include the player inventory. This packet will only be sent for the currently opened window while the player is performing actions, even if it affects the player inventory. After the window is closed, a number of these packets are sent to update the player's inventory window (0).
    private int slot;  // The slot that should be updated
    private ItemStack slotData;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readByte();
        slot = dis.readShort();
        slotData = dis.readSlot();
    }

    @Override
    public void process(GameContext context) {
        context.getSelf().getInventory().setSlot(slot, slotData);
        context.getMainThread().fireEvent(new SlotUpdateEvent(slot, slotData));
    }

}
