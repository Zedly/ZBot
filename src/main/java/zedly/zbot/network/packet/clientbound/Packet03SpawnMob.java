/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import zedly.zbot.GameContext;
import zedly.zbot.api.event.entity.EntitySpawnEvent;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.entity.CraftUnknown;
import zedly.zbot.Location;
import zedly.zbot.entity.EntityMeta;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet03SpawnMob implements ClientBoundPacket {

    public int entityID;
    private UUID uuid;
    private int type;
    private double x;
    private double y;
    private double z;
    private byte pitch;
    private byte headPitch;
    private byte yaw;
    private short velocityX;
    private short velocityY;
    private short velocityZ;
    private HashMap<Integer, EntityMeta> metaData;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        uuid = dis.readUUID();
        type = dis.readUnsignedByte();
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        yaw = dis.readByte();
        pitch = dis.readByte();
        headPitch = dis.readByte();
        velocityX = dis.readShort();
        velocityY = dis.readShort();
        velocityZ = dis.readShort();
        metaData = dis.readEntityMetaData();
    }

    @Override
    public void process(GameContext context) {
        try {
            CraftEntity ent = context.getSelf().getEnvironment().spawnEntity(type, entityID, new Location(x, y, z, yaw, pitch));
            ent.setMeta(metaData);
            //System.out.println("Spawned " + ent.getType() + " " + entityID);
            context.getMainThread().fireEvent(new EntitySpawnEvent(ent));
        } catch (Exception ex) {
            System.out.println("failed to set meta");
            ex.printStackTrace();
        }

    }
}
