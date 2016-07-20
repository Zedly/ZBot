/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import zedly.zbot.Location;
import zedly.zbot.GameContext;
import zedly.zbot.entity.CraftPlayer;
import zedly.zbot.environment.Data;
import zedly.zbot.entity.EntityMeta;
import zedly.zbot.event.PlayerSpawnEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet05SpawnPlayer implements ClientBoundPacket {
    private int entityID;
    private UUID playerUUID;
    private String playerName;
    private int dataCount;
    private Data[] theData;
    private double x;
    private double y;
    private double z;
    private byte yaw;
    private byte pitch;
    private short currentItem;
    private HashMap<Integer, EntityMeta> metaData;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        playerUUID = dis.readUUID();
        /*
        theData = new Data[dataCount];
        for (int i = 0; i < dataCount; i++) {
        theData[i] = new Data();
        theData[i].name = dis.readString();
        theData[i].value = dis.readString();
        theData[i].signature = dis.readString();
        }
         */
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        yaw = dis.readByte();
        pitch = dis.readByte();
        metaData = dis.readEntityMetaData();
    }

    public void process(GameContext context) {
        CraftPlayer ent = new CraftPlayer();
        ent.setEntityId(entityID);
        ent.setLocation(new Location(x, y, z, yaw, pitch));
        ent.setName(playerName);
        ent.setUUID(playerUUID);
        ent.setMeta(metaData);
        context.getSelf().getEnvironment().addEntity(ent);
        context.getMainThread().fireEvent(new PlayerSpawnEvent(ent));
    }
    
}
