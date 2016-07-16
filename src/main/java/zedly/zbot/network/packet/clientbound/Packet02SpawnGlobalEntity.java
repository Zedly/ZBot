/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.entity.CraftUnknown;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet02SpawnGlobalEntity implements ClientBoundPacket {
    int entityID;
    private byte type;
    private double x;
    private double y;
    private double z;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        type = dis.readByte();
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
    }
    
    public void process(GameContext context) {
        CraftEntity ent = context.getSelf().getEnvironment().spawnEntity(CraftUnknown.class, entityID, new Location(x, y, z));
    }
    
}
