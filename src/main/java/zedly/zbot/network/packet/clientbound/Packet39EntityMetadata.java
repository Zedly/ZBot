/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

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

public class Packet39EntityMetadata implements ClientBoundPacket {

    public int entityID;
    private HashMap<Integer, EntityMeta> metaData;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        metaData = dis.readEntityMetaData();
    }

    @Override
    public void process(GameContext context) {
        if (entityID == context.getSelf().getEntityId()) {
            context.getSelf().setMeta(metaData);
        } else {
            CraftEntity ce = context.getSelf().getEnvironment().getEntityById(entityID);
            if (ce == null) {
                System.err.println("Entity " + entityID + " is null!");
                return;
            }
            
            context.getMainThread().fireEvent(new EntityMetadataEvent(ce, metaData));
            
            try {
                List<Event> events = ce.setMeta(metaData);
                for (Event e : events) {
                    context.getMainThread().fireEvent(e);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
