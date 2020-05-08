package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.UUID;
import zedly.zbot.EntityType;
import zedly.zbot.Location;
import zedly.zbot.GameContext;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.entity.CraftPlayer;
import zedly.zbot.event.entity.EntitySpawnEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
/**
 * This packet is sent by the server when a player comes into visible range,
 * <i>not</i> when a player joins.
 */
public class Packet05SpawnPlayer implements ClientBoundPacket {

    private int entityID;  // Player's EID
    private UUID playerUUID;  // See below for notes on <span class="plainlinks"><a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Server.properties%23online-mode">offline mode</a></span> and NPCs
    private double x;
    private double y;
    private double z;
    private int yaw;
    private int pitch;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        playerUUID = dis.readUUID();
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        yaw = dis.readUnsignedByte();
        pitch = dis.readUnsignedByte();
    }

    @Override
    public void process(GameContext context) {
        CraftEntity ent = context.getSelf().getEnvironment().spawnEntity(CraftPlayer.class, entityID, new Location(x, y, z, yaw, pitch));
        //System.out.println("Spawned " + ent.getType() + " " + entityID);
        context.getMainThread().fireEvent(new EntitySpawnEvent(ent));
    }

}
