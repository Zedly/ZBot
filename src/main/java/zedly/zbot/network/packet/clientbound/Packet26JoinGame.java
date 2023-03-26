package    zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.server.NBTBase;

/**
 * @author Dennis
 */

/**
* See <a href="/Protocol_Encryption" title="Protocol Encryption">Protocol Encryption</a> for information on logging in.
*/

public class Packet26JoinGame implements ClientBoundPacket {
    private int entityID;  // The player's Entity ID (EID)
    private boolean isHardcore;
    private int gamemode;  // 0: Survival, 1: Creative, 2: Adventure, 3: Spectator. Bit 3 (0x8) is the hardcore flag.
    private int previousGamemode;  // 0: Survival, 1: Creative, 2: Adventure, 3: Spectator. Bit 3 (0x8) is the hardcore flag.
    private ArrayList<String> dimensionNames = new ArrayList<>();
    private NBTBase dimensionCodec;
    private NBTBase dimension;
    private int dimensionName;  // -1: Nether, 0: Overworld, 1: End; also, note that this is not a VarInt but instead a regular int.
    private long hashedseed;  // First 8 bytes of the SHA-256 hash of the world's seed.
    private int maxPlayers;  // Was once used by the client to draw the player list, but now is ignored
    private String levelType;  // default, flat, largeBiomes, amplified, customized, buffet, default_1_1
    private int viewDistance;  // Render distance (2-32)
    private int simulationDistance;
    private boolean reducedDebugInfo;  // If true, a Notchian client shows reduced information on the <span class="plainlinks"><a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Debug_screen">debug screen</a></span>.  For servers in development, this should almost always be false.
    private boolean enablerespawnscreen;  // Set to false when the doImmediateRespawn gamerule is true
    private boolean isDebug;  // Set to false when the doImmediateRespawn gamerule is true
    private boolean isFlat;  // Set to false when the doImmediateRespawn gamerule is true


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readInt();
        isHardcore = dis.readBoolean();
        gamemode = dis.readUnsignedByte();
        previousGamemode = dis.readUnsignedByte();
        int count = dis.readVarInt();
        for(int i = 0; i < count; i++) {
            dimensionNames.add(dis.readString());
        }
        dimensionCodec = dis.readNBT();
        dimension = dis.readNBT();
        dimensionName = dis.readInt();
        hashedseed = dis.readLong();
        maxPlayers = dis.readUnsignedByte();
        viewDistance = dis.readVarInt();
        simulationDistance = dis.readVarInt();
        reducedDebugInfo = dis.readBoolean();
        enablerespawnscreen = dis.readBoolean();
        isDebug = dis.readBoolean();
        isFlat = dis.readBoolean();
    }

    @Override
    public void process(GameContext context) {
        context.getSelf().setEntityId(entityID);
        context.getPluginManager().onJoin();
        context.getSelf().getEnvironment().reset(dimension);
        //context.getMainThread().fireEvent(new SelfJoinEvent(entityID, gamemode));
    }

}