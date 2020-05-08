package   zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* This packet as well as <a href="#Player_Position">Player Position</a>, <a href="#Player_Look">Player Look</a>, and <a href="#Player_Position_And_Look_2">Player Position And Look</a> are called the “serverbound movement packets”. Vanilla clients will send Player Position once every 20 ticks even for a stationary player.
*/


/**
* This packet as well as <a href="#Player_Position">Player Position</a>, <a href="#Player_Look">Player Look</a>, and <a href="#Player_Position_And_Look_2">Player Position And Look</a> are called the “serverbound movement packets”. Vanilla clients will send Player Position once every 20 ticks even for a stationary player.
*/

public class Packet14Player implements ServerBoundPacket {
    private final boolean onGround;  // True if the client is on the ground, false otherwise


    public Packet14Player(boolean onGround) {
        this.onGround = onGround;
    }

    @Override
    public int opCode() {
        return 0x14;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeBoolean(onGround);
    }
}
