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
 * This packet is sent by the server when an entity rotates and moves. Since a
 * short range is limited from -32768 to 32767, and movement is offset of
 * fixed-point numbers, this packet allows at most 8 blocks movement in any
 * direction. (<code>-32768 / (32 * 128) == -8</code>)
 */
public class Packet2AEntityPositionandRotation implements ClientBoundPacket {

    private int entityID;
    private int deltaX;  // Change in X position as <code>(currentX * 32 - prevX * 32) * 128</code>
    private int deltaY;  // Change in Y position as <code>(currentY * 32 - prevY * 32) * 128</code>
    private int deltaZ;  // Change in Z position as <code>(currentZ * 32 - prevZ * 32) * 128</code>
    private int yaw;  // New angle, not a delta
    private int pitch;  // New angle, not a delta
    private boolean onGround;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        deltaX = dis.readShort();
        deltaY = dis.readShort();
        deltaZ = dis.readShort();
        yaw = dis.readUnsignedByte();
        pitch = dis.readUnsignedByte();
        onGround = dis.readBoolean();
    }

    @Override
    public void process(GameContext context) {
        CraftEntity ent = context.getSelf().getEnvironment().getEntityById(entityID);
        if (ent != null) {
            Location oloc = ent.getLocation();
            Location loc = new Location(oloc.getX() + deltaX / 4096.0, oloc.getY() + deltaY / 4096.0, oloc.getZ() + deltaZ / 4096.0, yaw, pitch);
            context.getMainThread().fireEvent(new EntityMoveEvent(ent, oloc, loc));
            ent.setLocation(loc);
        }
    }

}
