package   zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.event.entity.EntityMoveEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import zedly.zbot.Location;


/**
* This packet is sent by the server when an entity rotates.
*/


/**
* This packet is sent by the server when an entity rotates.
*/

public class Packet2AEntityLook implements ClientBoundPacket {
    private int entityID;
    private int yaw;  // New angle, not a delta
    private int pitch;  // New angle, not a delta
    private boolean onGround;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        yaw = dis.readUnsignedByte();
        pitch = dis.readUnsignedByte();
        onGround = dis.readBoolean();
    }

    @Override
    public void process(GameContext context) {
        CraftEntity ent = context.getSelf().getEnvironment().getEntityById(entityID);
        if (ent != null) {
            Location oloc = ent.getLocation();
            Location loc = new Location(oloc.getX(), oloc.getY(), oloc.getZ(), yaw, pitch);
            context.getMainThread().fireEvent(new EntityMoveEvent(ent, oloc, loc));
            ent.setLocation(loc);
        }    }

}
