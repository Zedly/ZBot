package     zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */


/**
* The server will frequently send out a keep-alive, each containing a random ID. The client must respond with the same packet.
*/


/**
* Response to the clientbound packet (<a href="#Ping_.28play.29">Ping</a>) with the same id.
*/

public class Packet20Pong implements ServerBoundPacket {
    private final int iD;  // id is the same as the ping packet


    public Packet20Pong(int iD) {
        this.iD = iD;
    }

    @Override
    public int opCode() {
        return 0x20;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeInt(iD);
    }
}
