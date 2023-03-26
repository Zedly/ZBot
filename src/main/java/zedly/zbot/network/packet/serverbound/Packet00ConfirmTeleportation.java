package     zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* Sent by client as confirmation of <a href="#Synchronize_Player_Position">Synchronize Player Position</a>.
*/

public class Packet00ConfirmTeleportation implements ServerBoundPacket {
    private final int iD;  // The ID given by the <a href="#Synchronize_Player_Position">Synchronize Player Position</a> packet.

    public Packet00ConfirmTeleportation(int iD) {
        this.iD = iD;
    }

    @Override
    public int opCode() {
        return 0x00;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeInt(iD);
    }
}
