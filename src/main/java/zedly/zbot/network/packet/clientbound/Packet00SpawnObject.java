package zedly.zbot.network.packet.clientbound;

import zedly.zbot.Location;
import zedly.zbot.GameContext;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.environment.ObjectData;
import zedly.zbot.api.event.entity.EntitySpawnEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import java.util.UUID;
import zedly.zbot.entity.CraftObject;
import zedly.zbot.entity.CraftUnknown;

public class Packet00SpawnObject implements ClientBoundPacket {

    private int entityID;
    private UUID uuid;
    private byte type;
    private double x;
    private double y;
    private double z;
    private byte pitch;
    private byte yaw;
    private ObjectData objectData;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        uuid = dis.readUUID();
        type = dis.readByte();
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        pitch = dis.readByte();
        yaw = dis.readByte();
        objectData = new ObjectData();
        objectData.id = dis.readInt();
        objectData.x = dis.readShort();
        objectData.y = dis.readShort();
        objectData.z = dis.readShort();
    }

    @Override
    public void process(GameContext context) {
        CraftObject ent = context.getSelf().getEnvironment().spawnObject(type, entityID, objectData.id, new Location(x, y, z, yaw, pitch));
        //ent.setVelocity(velocity);
        context.getMainThread().fireEvent(new EntitySpawnEvent(ent));
    }
}
