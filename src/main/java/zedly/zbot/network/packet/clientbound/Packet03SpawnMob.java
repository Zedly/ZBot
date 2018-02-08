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
* Sent by the server when a mob entity is spawned.
*/

public class Packet03SpawnMob implements ClientBoundPacket {
    private int entityID;
    private UUID entityUUID;
    private int type;  // The type of mob. See <a href="/Entities#Mobs" class="mw-redirect" title="Entities">Entities#Mobs</a>
    private double x;
    private double y;
    private double z;
    private int yaw;
    private int pitch;
    private int headPitch;
    private int velocityX;  // Same units as <a href="#Entity_Velocity">Entity Velocity</a>
    private int velocityY;  // Same units as <a href="#Entity_Velocity">Entity Velocity</a>
    private int velocityZ;  // Same units as <a href="#Entity_Velocity">Entity Velocity</a>
    private HashMap<Integer, EntityMeta> metadata;


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
        headPitch = dis.readUnsignedByte();
        velocityX = dis.readShort();
        velocityY = dis.readShort();
        velocityZ = dis.readShort();
        metadata = dis.readEntityMetaData();
    }

    @Override
    public void process(GameContext context) {
        try {
            CraftEntity ent = context.getSelf().getEnvironment().spawnEntity(type, entityID, new Location(x, y, z, yaw, pitch));
            ent.setMeta(metadata);
            //System.out.println("Spawned " + ent.getType() + " " + entityID);
            context.getMainThread().fireEvent(new EntitySpawnEvent(ent));
        } catch (Exception ex) {
            System.out.println("failed to set meta");
            ex.printStackTrace();
        }

    }

}
