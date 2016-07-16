/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.GameContext;
import zedly.zbot.api.event.SelfTeleportEvent;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.packet.serverbound.Packet00TeleportConfirm;

/**
 *
 * @author Dennis
 */
public class Packet2EPlayerPositionAndLook implements ClientBoundPacket {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private byte flags;
    private int teleportId;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        yaw = dis.readFloat();
        pitch = dis.readFloat();
        flags = dis.readByte();
        teleportId = dis.readVarInt();
    }

    public void process(GameContext context) {
        context.getUpThread().sendPacket(new Packet00TeleportConfirm(teleportId));
        Location l = new Location(x, y, z, yaw, pitch);
        context.getSelf().moveTo(l);
        context.getMainThread().fireEvent(new SelfTeleportEvent(l, teleportId));
    }
}
