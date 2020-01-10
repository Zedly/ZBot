package   zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* The latter 2 floats are used to indicate the field of view and flying speed respectively, while the first byte is used to determine the value of 4 booleans.
*/


/**
* The latter 2 floats are used to indicate the field of view and flying speed respectively, while the first byte is used to determine the value of 4 booleans.
*/

public class Packet31PlayerAbilities implements ClientBoundPacket {
    private int flags;  // Bit field, see below
    private double flyingSpeed;  // 0.05 by default
    private double fieldofViewModifier;  // Modifies the field of view, like a speed potion. A Notchian server will use the same value as the movement speed sent in the <a href="/Protocol#Entity_Properties" title="Protocol">Entity Properties</a> packet, which defaults to 0.1 for players.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        flags = dis.readByte();
        flyingSpeed = dis.readFloat();
        fieldofViewModifier = dis.readFloat();
    }

}
