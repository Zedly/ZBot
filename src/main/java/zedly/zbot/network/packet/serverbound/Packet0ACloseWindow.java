package   zedly.zbot.network.packet.serverbound;

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


/**
* This packet is sent by the client when closing a window.
*/

public class Packet0ACloseWindow implements ServerBoundPacket {
    private final int windowID;  // This is the ID of the window that was closed. 0 for player inventory.


    public Packet0ACloseWindow(int windowID) {
        this.windowID = windowID;
    }

    @Override
    public int opCode() {
        return 0x0A;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(windowID);
    }
}
