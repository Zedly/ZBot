package   zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * @author Dennis
 */

/**
* See <a href="/Protocol_Encryption" title="Protocol Encryption">Protocol Encryption</a> for information on logging in.
*/


/**
* See <a href="/Protocol_Encryption" title="Protocol Encryption">Protocol Encryption</a> for information on logging in.
*/

public class Packet25JoinGame implements ClientBoundPacket {
    private int entityID;  // The player's Entity ID (EID)
    private int gamemode;  // 0: Survival, 1: Creative, 2: Adventure, 3: Spectator. Bit 3 (0x8) is the hardcore flag.
    private int dimension;  // -1: Nether, 0: Overworld, 1: End; also, note that this is not a VarInt but instead a regular int.
    private int maxPlayers;  // Was once used by the client to draw the player list, but now is ignored
    private String levelType;  // default, flat, largeBiomes, amplified, customized, buffet, default_1_1
    private int viewDistance;  // Render distance (2-32)
    private boolean reducedDebugInfo;  // If true, a Notchian client shows reduced information on the <span class="plainlinks"><a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Debug_screen">debug screen</a></span>.  For servers in development, this should almost always be false.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readInt();
        gamemode = dis.readUnsignedByte();
        dimension = dis.readInt();
        maxPlayers = dis.readUnsignedByte();
        levelType = dis.readString();
        viewDistance = dis.readVarInt();
        reducedDebugInfo = dis.readBoolean();
    }

}
