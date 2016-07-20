/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
//import zedly.zbot.event.entity.EntityStatusEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import zedly.zbot.event.Event;
import zedly.zbot.entity.CraftEntity;

/**
 * @author Dennis
 */
public class Packet1BEntityStatus implements ClientBoundPacket {

    private int entityId;
    private byte entityStatus;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityId = dis.readInt();
        entityStatus = dis.readByte();
    }

    @Override
    public void process(GameContext context) {
        if (entityId == context.getSelf().getEntityId()) {
            context.getSelf().setStatus(entityStatus);
        } else {
            CraftEntity ce = context.getSelf().getEnvironment().getEntityById(entityId);
            if (ce == null) {
                System.err.println("Entity status: " + entityId + " is null!");
            } else {
                Event evt = ce.setStatus(entityStatus);
                if (evt != null) {
                    context.getMainThread().fireEvent(evt);
                }
            }
        }
    }
}
