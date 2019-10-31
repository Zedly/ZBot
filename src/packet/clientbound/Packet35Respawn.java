package  zedly.zbot.network.packet.clientbound;

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

public class Packet35Respawn implements ClientBoundPacket {
    private int dimension;  // -1: The Nether, 0: The Overworld, 1: The End
    private int difficulty;  // 0: Peaceful, 1: Easy, 2: Normal, 3: Hard
    private int gamemode;  // 0: survival, 1: creative, 2: adventure, 3: spectator. The hardcore flag is not included
    private String levelType;  // Same as <a href="#Join_Game">Join Game</a>


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        dimension = dis.readInt();
        difficulty = dis.readUnsignedByte();
        gamemode = dis.readUnsignedByte();
        levelType = dis.readString();
    }

    @Override
    public void process(GameContext context) {
        context.getSelf().getEnvironment().reset(dimension);    }

}
