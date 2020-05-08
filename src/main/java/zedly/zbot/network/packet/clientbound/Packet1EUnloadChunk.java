package    zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.UnloadChunkEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * @author Dennis
 */

/**
* Tells the client to unload a chunk column.
*/

public class Packet1EUnloadChunk implements ClientBoundPacket {
    private int chunkX;  // Block coordinate divided by 16, rounded down
    private int chunkZ;  // Block coordinate divided by 16, rounded down


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        chunkX = dis.readInt();
        chunkZ = dis.readInt();
    }

}
