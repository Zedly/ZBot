/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

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
public class Packet48CollectItem implements ClientBoundPacket {

    private int collectedEntityID;
    private int collectorEntityID;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        collectedEntityID = dis.readVarInt();
        collectorEntityID = dis.readVarInt();
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
            context.getEventDispatcher().dispatchEvent(new EntityItemPickupEvent(context.getSelf(), item));
        } else {
            Entity collector = context.getSelf().getEnvironment().getEntityById(collectorEntityID);
            context.getEventDispatcher().dispatchEvent(new EntityItemPickupEvent(collector, item));
        }

    }
}
