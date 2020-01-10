package   zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* */


/**
* */

public class Packet1FResourcePackStatus implements ServerBoundPacket {
    private final int result;  // 0: successfully loaded, 1: declined, 2: failed download, 3: accepted


    public Packet1FResourcePackStatus(int result) {
        this.result = result;
    }

    @Override
    public int opCode() {
        return 0x1F;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(result);
    }
}
