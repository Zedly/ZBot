package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.event.entity.EntityMoveEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import zedly.zbot.Location;

public class Packet27EntityLook implements ClientBoundPacket {

    private int entityId;
    private byte yaw;
    private byte pitch;
    private boolean onGround;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityId = dis.readVarInt();
        yaw = dis.readByte();
        pitch = dis.readByte();
        onGround = dis.readBoolean();
    }

    public void process(GameContext context) {
        CraftEntity ent = context.getSelf().getEnvironment().getEntityById(entityId);
        if (ent != null) {
            Location oloc = ent.getLocation();
            Location loc = new Location(oloc.getX(), oloc.getY(), oloc.getZ(), yaw, pitch);
            context.getMainThread().fireEvent(new EntityMoveEvent(ent, oloc, loc));
            ent.setLocation(loc);
        }
    }
}
