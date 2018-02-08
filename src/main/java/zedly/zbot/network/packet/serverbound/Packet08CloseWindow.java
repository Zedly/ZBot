package  zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* This packet is sent by the client when closing a window.
*/

public class Packet08CloseWindow implements ServerBoundPacket {
    private final int windowID;  // This is the ID of the window that was closed. 0 for player inventory.


    public Packet08CloseWindow(int windowID) {
        this.windowID = windowID;
    }

    @Override
    public int opCode() {
        return 0x08;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(windowID);
    }
}
