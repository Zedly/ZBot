/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Packet06Animation implements ClientBoundPacket {

    public int entityID;
    public int animationId;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        animationId = dis.readUnsignedByte();
    }

    @Override
    public void process(GameContext context) {
        Entity ent = context.getSelf().getEnvironment().getEntityById(entityID);
        if (ent != null) {
            context.getEventDispatcher().dispatchEvent(new EntityAnimationEvent(ent, animationId));
        }
    }

}
