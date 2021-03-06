package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.event.WindowCloseEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
/**
 * This packet is sent from the server to the client when a window is forcibly
 * closed, such as when a chest is destroyed while it's open.
 */
public class Packet12CloseWindow implements ClientBoundPacket {

    private int windowID;  // This is the ID of the window that was closed. 0 for inventory.

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readUnsignedByte();
    }

    @Override
    public void process(GameContext context) {
        if (windowID == context.getSelf().getInventory().windowId()) {
            context.getEventDispatcher().dispatchEvent(new WindowCloseEvent(context.getSelf().getInventory()));
            context.getSelf().closeWindow(false);
        }
    }

}
