package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Used for a wide variety of game events, from weather to bed use to gamemode to demo messages.
*/

public class Packet1FGameEvent implements ClientBoundPacket {
    private int event;  // See below.
    private double value;  // Depends on Event.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        event = dis.readUnsignedByte();
        value = dis.readFloat();
    }

}
