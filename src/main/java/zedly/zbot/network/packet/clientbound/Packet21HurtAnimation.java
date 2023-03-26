package zedly.zbot.network.packet.clientbound;

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
public class Packet21HurtAnimation implements ClientBoundPacket {

    private int entityID;  // Player ID
    private float bobDirectionYaw;  // Animation ID (see below)

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        bobDirectionYaw = dis.readFloat();
    }

    @Override
    public void process(GameContext context) {
        Entity ent = context.getSelf().getEnvironment().getEntityById(entityID);
        if (ent != null) {
            //context.getEventDispatcher().dispatchEvent(new EntityAnimationEvent(ent, animation));
        }
    }
}