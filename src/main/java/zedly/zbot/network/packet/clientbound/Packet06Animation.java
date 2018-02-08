package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.entity.Entity;
import zedly.zbot.event.entity.EntityAnimationEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Sent whenever an entity should change animation.
*/

public class Packet06Animation implements ClientBoundPacket {
    private int entityID;  // Player ID
    private int animation;  // Animation ID (see below)


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        animation = dis.readUnsignedByte();
    }

    @Override
    public void process(GameContext context) {
        Entity ent = context.getSelf().getEnvironment().getEntityById(entityID);
        if (ent != null) {
            context.getEventDispatcher().dispatchEvent(new EntityAnimationEvent(ent, animation));
        }
    }

}
