package  zedly.zbot.network.packet.clientbound;

import zedly.zbot.Location;
import zedly.zbot.GameContext;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.environment.ObjectData;
import zedly.zbot.event.entity.EntitySpawnEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import java.util.UUID;
import zedly.zbot.entity.CraftObject;
import zedly.zbot.entity.CraftUnknown;


/**
* Sent by the server when a vehicle or other object is created.
*/

public class Packet00SpawnObject implements ClientBoundPacket {
    private int entityID;  // EID of the object
    private UUID objectUUID;
    private int type;  // The type of object (see <a href="/Entities#Objects" class="mw-redirect" title="Entities">Entities#Objects</a>)
    private double x;
    private double y;
    private double z;
    private int pitch;
    private int yaw;
    private int data;  // Meaning dependent on the value of the Type field, see <a href="/Object_Data" title="Object Data">Object Data</a> for details.
    private int velocityX;  // Same units as <a href="#Entity_Velocity">Entity Velocity</a>.  Always sent, but only used when Data is greater than 0 (except for some entities which always ignore it; see <a href="/Object_Data" title="Object Data">Object Data</a> for details).
    private int velocityY;
    private int velocityZ;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        objectUUID = dis.readUUID();
        type = dis.readByte();
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
        CraftObject ent = context.getSelf().getEnvironment().spawnObject(type, entityID, data, new Location(x, y, z, yaw, pitch));
        //ent.setVelocity(velocity);
        context.getMainThread().fireEvent(new EntitySpawnEvent(ent));
    }

}
