package  zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import zedly.zbot.entity.EntityMeta;
import zedly.zbot.entity.EntityMeta;
import zedly.zbot.event.Event;
import zedly.zbot.event.entity.EntityMetadataEvent;

/**
 * Updates one or more
 * <a href="/Entities#Entity_Metadata_Format" class="mw-redirect" title="Entities">metadata</a>
 * properties for an existing entity. Any properties not included in the
 * Metadata field are left unchanged.
 */

/**
* Updates one or more <a href="/Entities#Entity_Metadata_Format" class="mw-redirect" title="Entities">metadata</a> properties for an existing entity. Any properties not included in the Metadata field are left unchanged.
*/

public class Packet44EntityMetadata implements ClientBoundPacket {
    private int entityID;
    private HashMap<Integer, EntityMeta> metadata;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        metadata = dis.readEntityMetaData();
    }

    @Override
    public void process(GameContext context) {
        if (entityID == context.getSelf().getEntityId()) {
            context.getSelf().setMeta(metadata);
        } else {
            CraftEntity ce = context.getSelf().getEnvironment().getEntityById(entityID);
            if (ce == null) {
                System.err.println("Entity " + entityID + " is null!");
                return;
            }

            context.getMainThread().fireEvent(new EntityMetadataEvent(ce, metadata));

            try {
                List<Event> events = ce.setMeta(metadata);
                for (Event e : events) {
                    context.getMainThread().fireEvent(e);
                }
            } catch (Exception ex) {
                System.err.println("Failed to set meta for " + ce + ":");
                System.err.println(ex.getMessage());
                System.err.println(metadata);
            }
        }
    }

}
