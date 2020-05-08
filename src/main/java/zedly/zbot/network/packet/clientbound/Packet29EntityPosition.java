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
public class Packet29EntityPosition implements ClientBoundPacket {

    private int entityID;
    private int deltaX;  // Change in X position as <code>(currentX * 32 - prevX * 32) * 128</code>
    private int deltaY;  // Change in Y position as <code>(currentY * 32 - prevY * 32) * 128</code>
    private int deltaZ;  // Change in Z position as <code>(currentZ * 32 - prevZ * 32) * 128</code>
    private boolean onGround;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        deltaX = dis.readShort();
        deltaY = dis.readShort();
        deltaZ = dis.readShort();
        onGround = dis.readBoolean();
    }

    @Override
    public void process(GameContext context) {
        CraftEntity ent = context.getSelf().getEnvironment().getEntityById(entityID);
        if (ent != null) {
            Location oloc = ent.getLocation();
            Location loc = new Location(oloc.getX() + deltaX / 4096.0, oloc.getY() + deltaY / 4096.0, oloc.getZ() + deltaZ / 4096.0, oloc.getYaw(), oloc.getPitch());
            context.getMainThread().fireEvent(new EntityMoveEvent(ent, oloc, loc));
            ent.setLocation(loc);
        }
    }

}
