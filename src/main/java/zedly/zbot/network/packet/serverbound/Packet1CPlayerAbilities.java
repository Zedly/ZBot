package     zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */


/**
* The latter 2 fields are used to indicate the walking and flying speeds respectively, while the first field is used to determine the value of 4 booleans.
*/


/**
* The vanilla client sends this packet when the player starts/stops flying with the Flags parameter changed accordingly.
*/

public class Packet1CPlayerAbilities implements ServerBoundPacket {
    private final int flags;  // Bit mask. 0x02: is flying.


    public Packet1CPlayerAbilities(int flags) {
        this.flags = flags;
    }

    @Override
    public int opCode() {
        return 0x1C;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(flags);
    }
}
