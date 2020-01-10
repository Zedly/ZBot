package   zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.event.entity.EntityTeleportEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* This packet is sent by the server when an entity moves more than 4 blocks.
*/


/**
* This packet is sent by the server when an entity moves more than 8 blocks.
*/

public class Packet56EntityTeleport implements ClientBoundPacket {
    private int entityID;
    private double x;
    private double y;
    private double z;
    private int yaw;  // New angle, not a delta
    private int pitch;  // New angle, not a delta
    private boolean onGround;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        yaw = dis.readUnsignedByte();
        pitch = dis.readUnsignedByte();
        onGround = dis.readBoolean();
    }

    @Override
    public void process(GameContext context) {
        CraftEntity ent = context.getSelf().getEnvironment().getEntityById(entityID);
        if(ent != null) {
            Location oloc = ent.getLocation();
            Location loc = new Location(x, y, z, yaw, pitch);
            context.getMainThread().fireEvent(new EntityTeleportEvent(ent, oloc, loc));
            ent.setLocation(loc);
        }    }

}
