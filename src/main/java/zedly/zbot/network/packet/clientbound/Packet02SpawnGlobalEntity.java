package  zedly.zbot.network.packet.clientbound;

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

/**
* With this packet, the server notifies the client of thunderbolts striking within a 512 block radius around the player. The coordinates specify where exactly the thunderbolt strikes.
*/

public class Packet02SpawnGlobalEntity implements ClientBoundPacket {
    private int entityID;  // The EID of the thunderbolt
    private int type;  // The global entity type, currently always 1 for thunderbolt
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

    @Override
    public void process(GameContext context) {
        CraftEntity ent = context.getSelf().getEnvironment().spawnEntity(CraftUnknown.class, entityID, new Location(x, y, z));    }

}
