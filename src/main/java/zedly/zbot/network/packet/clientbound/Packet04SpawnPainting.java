/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.UUID;
import zedly.zbot.Location;
import zedly.zbot.GameContext;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.entity.CraftUnknown;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet04SpawnPainting implements ClientBoundPacket {

    private int entityID;
    private String title;
    private UUID uuid;
    private int x;
    private int y;
    private int z;
    private int direction;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        uuid = dis.readUUID();
        title = dis.readString();
        Location loc = dis.readPosition();
        x = loc.getBlockX();
        y = loc.getBlockY();
        z = loc.getBlockZ();
        direction = dis.readUnsignedByte();
    }

    public void process(GameContext context) {
        CraftEntity ent = context.getSelf().getEnvironment().spawnEntity(CraftUnknown.class, entityID, new Location(x, y, z));
    }

}
