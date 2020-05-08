package  zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
//import zedly.zbot.event.entity.EntityStatusEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import zedly.zbot.event.Event;
import zedly.zbot.entity.CraftEntity;

/**
 * @author Dennis
 */
/**
 * Entity statuses generally trigger an animation for an entity. The available
 * statuses vary by the entity's type (and are available to subclasses of that
 * type as well).
 */

public class Packet1CEntityStatus implements ClientBoundPacket {
    private int entityID;
    private int entityStatus;  // See below


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readInt();
        entityStatus = dis.readByte();
    }

    @Override
    public void process(GameContext context) {
        if (entityID == context.getSelf().getEntityId()) {
            context.getSelf().setStatus(entityStatus);
        } else {
            CraftEntity ce = context.getSelf().getEnvironment().getEntityById(entityID);
            if (ce == null) {
                System.err.println("Entity status: " + entityID + " is null!");
            } else {
                Event evt = ce.setStatus(entityStatus);
                if (evt != null) {
                    context.getMainThread().fireEvent(evt);
                }
            }
        }
    }

}
