package     zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import net.minecraft.server.NBTBase;
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


/**
* To change the player's dimension (overworld/nether/end), send them a respawn packet with the appropriate dimension, followed by prechunks/chunks for the new dimension, and finally a position and look packet. You do not need to unload chunks, the client will do it automatically.
*/

public class Packet3DRespawn implements ClientBoundPacket {
    private NBTBase dimension;  // Valid dimensions are defined per dimension registry sent in <a href="#Join_Game">Join Game</a>
    private String dimensionName;  // Name of the dimension being spawned into.
    private long hashedseed;  // First 8 bytes of the SHA-256 hash of the world's seed. Used client side for biome noise
    private int gamemode;  // 0: survival, 1: creative, 2: adventure, 3: spectator. The hardcore flag is not included
    private int previousGamemode;  // -1: null 0: survival, 1: creative, 2: adventure, 3: spectator. The hardcore flag is not included. The previous gamemode. (More information needed)
    private boolean isDebug;  // True if the world is a <span class="plainlinks"><a rel="nofollow" class="external text" href="https://minecraft.fandom.com/wiki/Debug_mode">debug mode</a></span> world; debug mode worlds cannot be modified and have predefined blocks.
    private boolean isFlat;  // True if the world is a <span class="plainlinks"><a rel="nofollow" class="external text" href="https://minecraft.fandom.com/wiki/Superflat">superflat</a></span> world; flat worlds have different void fog and a horizon at y=0 instead of y=63.
    private boolean copymetadata;  // If false, metadata is reset on the respawned player entity.  Set to true for dimension changes (including the dimension change triggered by sending client status perform respawn to exit the end poem/credits), and false for normal respawns.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        dimension = dis.readNBT();
        dimensionName = dis.readString();
        hashedseed = dis.readLong();
        gamemode = dis.readUnsignedByte();
        previousGamemode = dis.readUnsignedByte();
        isDebug = dis.readBoolean();
        isFlat = dis.readBoolean();
        copymetadata = dis.readBoolean();
    }

    @Override
    public void process(GameContext context) {
    }
}
