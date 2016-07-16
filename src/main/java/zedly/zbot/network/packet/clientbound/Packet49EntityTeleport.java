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
import zedly.zbot.api.event.entity.EntityTeleportEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet49EntityTeleport implements ClientBoundPacket {
    private int entityId;
    private double x;
    private double y;
    private double z;
    private byte yaw;
    private byte pitch;
    private boolean onGround;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityId = dis.readVarInt();
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        yaw = dis.readByte();
        pitch = dis.readByte();
        onGround = dis.readBoolean();
    }
    
    @Override
    public void process(GameContext context) {
        CraftEntity ent = context.getSelf().getEnvironment().getEntityById(entityId);
        if(ent != null) {
            Location oloc = ent.getLocation();
            Location loc = new Location(x, y, z, yaw, pitch);
            context.getMainThread().fireEvent(new EntityTeleportEvent(ent, oloc, loc));
            ent.setLocation(loc);
        }
    }
}
