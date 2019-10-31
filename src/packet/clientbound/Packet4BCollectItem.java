package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.entity.Entity;
import zedly.zbot.entity.Item;
import zedly.zbot.event.entity.EntityItemPickupEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Sent by the server when someone picks up an item lying on the ground — its sole purpose appears to be the animation of the item flying towards you. It doesn't destroy the entity in the client memory, and it doesn't add it to your inventory. The server only checks for items to be picked up after each <a href="#Player_Position">Player Position</a> (and <a href="#Player_Position_And_Look">Player Position And Look</a>) packet sent by the client.
*/

public class Packet4BCollectItem implements ClientBoundPacket {
    private int collectedEntityID;
    private int collectorEntityID;
    private int pickupItemCount;  // Seems to be 1 for XP orbs, otherwise the number of items in the stack.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        collectedEntityID = dis.readVarInt();
        collectorEntityID = dis.readVarInt();
        pickupItemCount = dis.readVarInt();
    }

    @Override
    public void process(GameContext context) {
        Entity ent = context.getSelf().getEnvironment().getEntityById(collectedEntityID);
        if (ent == null) {
            System.err.println("Collect Item: Unknown entity ID " + collectedEntityID);
            return;
        } else if (!(ent instanceof Item)) {
            System.err.println("Collect Item: Entity " + collectedEntityID + " is not an item, but a " + ent.getType());
            return;
        }
        Item item = (Item) ent;
        if (collectorEntityID == context.getSelf().getEntityId()) {
            context.getEventDispatcher().dispatchEvent(new EntityItemPickupEvent(context.getSelf(), item, pickupItemCount));
        } else {
            Entity collector = context.getSelf().getEnvironment().getEntityById(collectorEntityID);
            context.getEventDispatcher().dispatchEvent(new EntityItemPickupEvent(collector, item, pickupItemCount));
        }
    }

}
