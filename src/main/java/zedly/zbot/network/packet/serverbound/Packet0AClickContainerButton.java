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

public class Packet0AClickContainerButton implements ServerBoundPacket {
    private final int windowID;  // The ID of the window sent by <a href="#Open_Screen">Open Screen</a>.
    private final int buttonID;  // Meaning depends on window type; see below.


    public Packet0AClickContainerButton(int windowID, int buttonID) {
        this.windowID = windowID;
        this.buttonID = buttonID;
    }

    @Override
    public int opCode() {
        return 0x0A;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(windowID);
        dos.writeByte(buttonID);
    }
}
