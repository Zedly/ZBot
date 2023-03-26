package     zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* Sent when the player's arm swings.
*/



/**
* Sent when the player's arm swings.
*/

public class Packet2CAnimation implements ServerBoundPacket {
    private final int hand;  // Hand used for the animation. 0: main hand, 1: off hand.


    public Packet2CAnimation(int hand) {
        this.hand = hand;
    }

    @Override
    public int opCode() {
        return 0x2C;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(hand);
    }
}
