package      zedly.zbot.network.packet.serverbound;

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


/**
* Response to the clientbound packet (<a href="#Ping_2">Ping</a>) with the same id. 
*/

public class Packet1DPong implements ServerBoundPacket {
    private final int iD;  // id is the same as the ping packet


    public Packet1DPong(int iD) {
        this.iD = iD;
    }

    @Override
    public int opCode() {
        return 0x1D;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeInt(iD);
    }
}
