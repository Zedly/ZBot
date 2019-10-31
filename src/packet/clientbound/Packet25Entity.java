package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* This packet may be used to initialize an entity.
*/

public class Packet25Entity implements ClientBoundPacket {
    private int entityID;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
    }

    @Override
    public void process(GameContext context) {    }

}
