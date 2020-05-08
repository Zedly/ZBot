package    zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* To change the player's dimension (overworld/nether/end), send them a respawn packet with the appropriate dimension, followed by prechunks/chunks for the new dimension, and finally a position and look packet. You do not need to unload chunks, the client will do it automatically.
*/

public class Packet3BRespawn implements ClientBoundPacket {
    private int dimension;  // -1: The Nether, 0: The Overworld, 1: The End
    private long hashedseed;  // First 8 bytes of the SHA-256 hash of the world's seed.
    private int gamemode;  // 0: survival, 1: creative, 2: adventure, 3: spectator. The hardcore flag is not included
    private String levelType;  // Same as <a href="#Join_Game">Join Game</a>


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        dimension = dis.readInt();
        hashedseed = dis.readLong();
        gamemode = dis.readUnsignedByte();
        levelType = dis.readString();
    }

}
