package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.GameContext;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.event.entity.EntityMoveEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
/**
 * This packet is sent by the server when an entity moves less then 8 blocks; if
 * an entity moves more than 8 blocks <a href="#Entity_Teleport">Entity
 * Teleport</a> should be sent instead.
 */
public class Packet2CEntityMovement implements ClientBoundPacket {

    private int entityID;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
    }
}
