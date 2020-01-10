package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* The latter 2 floats are used to indicate the field of view and flying speed respectively, while the first byte is used to determine the value of 4 booleans.
*/

public class Packet2CPlayerAbilities implements ClientBoundPacket {
    private int flags;  // Bit field, see below
    private double flyingSpeed;
    private double fieldofViewModifier;  // Modifies the field of view, like a speed potion. A Notchian server will use the same value as the movement speed (send in the <a href="/Protocol#Entity_Properties" title="Protocol">Entity Properties</a> packet).


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        flags = dis.readByte();
        flyingSpeed = dis.readFloat();
        fieldofViewModifier = dis.readFloat();
    }

}
