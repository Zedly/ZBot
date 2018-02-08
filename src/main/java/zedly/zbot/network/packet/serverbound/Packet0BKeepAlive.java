package  zedly.zbot.network.packet.serverbound;

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

public class Packet0BKeepAlive implements ServerBoundPacket {
    private final long keepAliveID;


    public Packet0BKeepAlive(long keepAliveID) {
        this.keepAliveID = keepAliveID;
    }

    @Override
    public int opCode() {
        return 0x0B;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeLong(keepAliveID);
    }
}
