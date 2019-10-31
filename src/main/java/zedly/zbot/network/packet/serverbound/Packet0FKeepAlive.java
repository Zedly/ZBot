package   zedly.zbot.network.packet.serverbound;

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
* The server will frequently send out a keep-alive, each containing a random ID. The client must respond with the same packet.
*/

public class Packet0FKeepAlive implements ServerBoundPacket {
    private final long keepAliveID;


    public Packet0FKeepAlive(long keepAliveID) {
        this.keepAliveID = keepAliveID;
    }

    @Override
    public int opCode() {
        return 0x0F;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeLong(keepAliveID);
    }
}
