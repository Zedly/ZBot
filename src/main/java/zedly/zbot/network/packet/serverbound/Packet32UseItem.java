package     zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* Sent when pressing the Use Item key (default: right click) with an item in hand.
*/


/**
* Sent when pressing the Use Item key (default: right click) with an item in hand.
*/

public class Packet32UseItem implements ServerBoundPacket {
    private final int hand;  // Hand used for the animation. 0: main hand, 1: off hand.
    private final int sequence;


    public Packet32UseItem(int hand, int sequence) {
        this.hand = hand;
        this.sequence = sequence;
    }

    @Override
    public int opCode() {
        return 0x32;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(hand);
        dos.writeVarInt(sequence);
    }
}
