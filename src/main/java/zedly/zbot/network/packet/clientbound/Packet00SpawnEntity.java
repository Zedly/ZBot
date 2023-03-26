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
/**
 * Sent by the server when a mob entity is spawned.
 */
/**
 * Sent by the server when a living entity is spawned.
 */

/**
* Sent by the server when a vehicle or other non-living entity is created.
*/

public class Packet00SpawnEntity implements ClientBoundPacket {
    private int entityID;  // Entity ID.
    private UUID objectUUID;
    private int type;  // The type of entity (same as in <a href="#Spawn_Living_Entity">Spawn Living Entity</a>).
    private double x;
    private double y;
    private double z;
    private int pitch;  // To get the real pitch, you must divide this by (256.0F / 360.0F)
    private int yaw;  // To get the real yaw, you must divide this by (256.0F / 360.0F)
    private int data;  // Meaning dependent on the value of the Type field, see <a href="/Object_Data" title="Object Data">Object Data</a> for details.
    private int velocityX;  // Same units as <a href="#Entity_Velocity">Entity Velocity</a>.  Always sent, but only used when Data is greater than 0 (except for some entities which always ignore it; see <a href="/Object_Data" title="Object Data">Object Data</a> for details).
    private int velocityY;
    private int velocityZ;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        objectUUID = dis.readUUID();
        type = dis.readVarInt();
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        pitch = dis.readUnsignedByte();
        yaw = dis.readUnsignedByte();
        data = dis.readInt();
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
