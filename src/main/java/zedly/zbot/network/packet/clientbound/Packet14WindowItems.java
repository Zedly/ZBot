package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.WindowItemsEvent;
import zedly.zbot.inventory.ItemStack;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import zedly.zbot.event.WindowOpenFinishEvent;
import zedly.zbot.inventory.CraftInventory;
import zedly.zbot.inventory.CraftPlayerInventory;

/**
 * @author Dennis
 */
/**
 * Sent by the server when items in multiple slots (in a window) are
 * added/removed. This includes the main inventory, equipped armour and crafting
 * slots.
 */
public class Packet14WindowItems implements ClientBoundPacket {

    private int windowID;  // The ID of window which items are being sent for. 0 for player inventory.
    private int count;  // Number of elements in the following array
    private ItemStack[] slotData;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readUnsignedByte();
        count = dis.readShort();
        slotData = new ItemStack[count];
        for (int i = 0; i < count; i++) {
            slotData[i] = dis.readSlot();
        }
    }

    @Override
    public void process(GameContext context) {
        boolean initialized = context.getSelf().getInventory().isInitialized();
        CraftInventory inv = context.getSelf().getInventory();
        for (int i = 0; i < slotData.length; i++) {
            inv.setSlot(i, slotData[i]);
        }
        context.getMainThread().fireEvent(new WindowItemsEvent(windowID, slotData));
        if (!(inv instanceof CraftPlayerInventory) && !initialized) {
            context.getEventDispatcher().dispatchEvent(new WindowOpenFinishEvent(context.getSelf().getInventory()));
        }
    }
}
