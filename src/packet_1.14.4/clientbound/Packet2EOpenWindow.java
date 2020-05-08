package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.event.WindowOpenStartEvent;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.mappings.InventoryType;

/**
 *
 * @author Dennis
 */

/**
* This is sent to the client when it should open an inventory, such as a chest, workbench, or furnace. This message is not sent anywhere for clients opening their own inventory.  For horses, use <a href="#Open_Horse_Window">Open Horse Window</a>.
*/

public class Packet2EOpenWindow implements ClientBoundPacket {
    private int windowID;  // A unique id number for the window to be displayed. Notchian server implementation is a counter, starting at 1.
    private int windowType;  // The window type to use for display. Contained in the <code>minecraft:menu</code> regisry; see <a href="/Inventory" title="Inventory">Inventory</a> for the different values.
    private String windowTitle;  // The title of the window


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readVarInt();
        windowType = dis.readVarInt();
        windowTitle = dis.readString();
    }

    @Override
    public void process(GameContext context) {
        context.getSelf().openWindow(InventoryType.fromProtocolId(windowType), windowID, windowTitle);
        context.getEventDispatcher().dispatchEvent(new WindowOpenStartEvent(context.getSelf().getInventory()));
    }

}
