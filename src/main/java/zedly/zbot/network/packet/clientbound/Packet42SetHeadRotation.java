package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.event.entity.EntityMoveEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
/**
 * Changes the direction an entity's head is facing.
 */

/**
* Changes the direction an entity's head is facing.
*/

public class Packet42SetHeadRotation implements ClientBoundPacket {
    private int entityID;
    private int headYaw;  // New angle, not a delta.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        headYaw = dis.readUnsignedByte();
    }

    @Override
    public void process(GameContext context) {
        CraftEntity ent = context.getSelf().getEnvironment().getEntityById(entityID);
        if (ent != null) {
            Location oloc = ent.getLocation();
            Location loc = new Location(oloc.getX(), oloc.getY(), oloc.getZ(), headYaw, oloc.getPitch());
            context.getMainThread().fireEvent(new EntityMoveEvent(ent, oloc, loc));
            ent.setLocation(loc);
        }
    }
}
