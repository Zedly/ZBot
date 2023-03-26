package    zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* To change the player's dimension (overworld/nether/end), send them a respawn packet with the appropriate dimension, followed by prechunks/chunks for the new dimension, and finally a position and look packet. You do not need to unload chunks, the client will do it automatically.
*/

public class Packet41Respawn implements ClientBoundPacket {
    private String dimensionType;  // -1: The Nether, 0: The Overworld, 1: The End
    private String dimensionName;  // -1: The Nether, 0: The Overworld, 1: The End
    private long hashedseed;  // First 8 bytes of the SHA-256 hash of the world's seed.
    private int gamemode;  // 0: survival, 1: creative, 2: adventure, 3: spectator. The hardcore flag is not included
    private int previousGamemode;  // 0: survival, 1: creative, 2: adventure, 3: spectator. The hardcore flag is not included
    private boolean isDebug;  // 0: survival, 1: creative, 2: adventure, 3: spectator. The hardcore flag is not included
    private boolean isFlat;  // 0: survival, 1: creative, 2: adventure, 3: spectator. The hardcore flag is not included
    private boolean copyMetadata;  // 0: survival, 1: creative, 2: adventure, 3: spectator. The hardcore flag is not included
    private String deathDimensionName;  // 0: survival, 1: creative, 2: adventure, 3: spectator. The hardcore flag is not included
    private Location deathLocation;  // 0: survival, 1: creative, 2: adventure, 3: spectator. The hardcore flag is not included


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        dimensionType = dis.readString();
        dimensionName = dis.readString();
        hashedseed = dis.readLong();
        gamemode = dis.readUnsignedByte();
        previousGamemode = dis.readUnsignedByte();
        isDebug = dis.readBoolean();
        isFlat = dis.readBoolean();
        copyMetadata = dis.readBoolean();
        boolean hasDeathLocation = dis.readBoolean();
        if(hasDeathLocation) {
            deathDimensionName = dis.readString();
            deathLocation = dis.readPosition();
        }
    }

    @Override
    public void process(GameContext context) {
    }
}
