package     zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.inventory.ItemStack;
import zedly.zbot.network.ExtendedDataOutputStream;

/**
 *
 * @author Dennis
 */


/**
* Used when clicking on window buttons.  Until 1.14, this was only used by enchantment tables.
*/

public class Packet07ClickWindowButton implements ServerBoundPacket {
    private final int windowID;  // The ID of the window sent by <a href="#Open_Window">Open Window</a>.
    private final int buttonID;  // Meaning depends on window type; see below.


    public Packet07ClickWindowButton(int windowID, int buttonID) {
        this.windowID = windowID;
        this.buttonID = buttonID;
    }

    @Override
    public int opCode() {
        return 0x07;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(windowID);
        dos.writeByte(buttonID);
    }
}
