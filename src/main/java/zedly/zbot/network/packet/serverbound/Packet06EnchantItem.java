package  zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* */

public class Packet06EnchantItem implements ServerBoundPacket {
    private final int windowID;  // The ID of the enchantment table window sent by <a href="#Open_Window">Open Window</a>
    private final int enchantment;  // The position of the enchantment on the enchantment table window, starting with 0 as the topmost one


    public Packet06EnchantItem(int windowID, int enchantment) {
        this.windowID = windowID;
        this.enchantment = enchantment;
    }

    @Override
    public int opCode() {
        return 0x06;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(windowID);
        dos.writeByte(enchantment);
    }
}
