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
import zedly.zbot.entity.CraftExperienceOrb;
import zedly.zbot.entity.CraftUnknown;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet01SpawnExperienceOrb implements ClientBoundPacket {
    private int entityID;
    private double x;
    private double y;
    private double z;
    private short count;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        count = dis.readShort();
    }
    
    @Override
    public void process(GameContext context) {
        CraftExperienceOrb orb = (CraftExperienceOrb) context.getSelf().getEnvironment().spawnEntity(CraftExperienceOrb.class, entityID, new Location(x, y, z));
        orb.setCount(count);
    }
    
}
