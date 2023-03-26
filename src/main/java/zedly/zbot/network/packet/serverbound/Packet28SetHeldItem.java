package     zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* Sent when the player changes the slot selection
*/


/**
* Sent when the player changes the slot selection
*/

public class Packet28SetHeldItem implements ServerBoundPacket {
    private final int slot;  // The slot which the player has selected (0–8).


    public Packet28SetHeldItem(int slot) {
        this.slot = slot;
    }

    @Override
    public int opCode() {
        return 0x28;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeShort(slot);
    }
}