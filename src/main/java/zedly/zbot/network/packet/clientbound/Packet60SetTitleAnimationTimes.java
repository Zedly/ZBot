package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* */

public class Packet60SetTitleAnimationTimes implements ClientBoundPacket {
    private int fadeIn;  // Ticks to spend fading in.
    private int stay;  // Ticks to keep the title displayed.
    private int fadeOut;  // Ticks to spend fading out, not when to start fading out.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        fadeIn = dis.readInt();
        stay = dis.readInt();
        fadeOut = dis.readInt();
    }

}
