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
 * slots. This packet with Window ID set to "0" is sent during the player
 * joining sequence to initialise the player's inventory.
 */
public class Packet12SetContainerContent implements ClientBoundPacket {

    private int windowID;  // The ID of window which items are being sent for. 0 for player inventory.
    private int stateID;  // The last received State ID from either a <a href="#Set_Container_Slot">Set Container Slot</a> or a <a href="#Set_Container_Content">Set Container Content</a> packet
    private ItemStack[] slotData;
    private ItemStack carriedItem;  // Item held by player.

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readUnsignedByte();
        stateID = dis.readVarInt();
        int count = dis.readVarInt();
        slotData = new ItemStack[count];
        for (int i = 0; i < count; i++) {
            slotData[i] = dis.readSlot();
        }
        carriedItem = dis.readSlot();
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
