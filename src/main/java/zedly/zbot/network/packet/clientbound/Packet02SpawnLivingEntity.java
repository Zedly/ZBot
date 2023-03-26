package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import zedly.zbot.GameContext;
import zedly.zbot.event.entity.EntitySpawnEvent;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.entity.CraftUnknown;
import zedly.zbot.Location;
import zedly.zbot.entity.EntityMeta;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
/**
 * Sent by the server when a living entity is spawned.
 */

/**
* Sent by the server when a living entity is spawned.
*/

public class Packet02SpawnLivingEntity implements ClientBoundPacket {
    private int entityID;
    private UUID entityUUID;
    private int type;  // The type of mob. See <a href="/Entity_metadata#Mobs" title="Entity metadata">Entity_metadata#Mobs</a>.
    private double x;
    private double y;
    private double z;
    private int yaw;
    private int pitch;
    private int headYaw;
    private int velocityX;  // Same units as <a href="#Entity_Velocity">Entity Velocity</a>.
    private int velocityY;  // Same units as <a href="#Entity_Velocity">Entity Velocity</a>.
    private int velocityZ;  // Same units as <a href="#Entity_Velocity">Entity Velocity</a>.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        entityUUID = dis.readUUID();
        type = dis.readVarInt();
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        yaw = dis.readUnsignedByte();
        pitch = dis.readUnsignedByte();
        headYaw = dis.readUnsignedByte();
        velocityX = dis.readShort();
        velocityY = dis.readShort();
        velocityZ = dis.readShort();
    }

    @Override
    public void process(GameContext context) {
        CraftEntity ent = context.getSelf().getEnvironment().spawnEntity(type, entityID, new Location(x, y, z, yaw, pitch));
        //System.out.println("Spawned " + ent.getType() + " " + entityID);
        context.getMainThread().fireEvent(new EntitySpawnEvent(ent));    }

}
