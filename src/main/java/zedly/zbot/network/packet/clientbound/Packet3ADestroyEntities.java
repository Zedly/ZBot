/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import zedly.zbot.GameContext;

/**
 * @author Dennis
 */
public class Packet3ADestroyEntities implements ClientBoundPacket {

    private int[] theInts;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        int count = dis.readVarInt();
        theInts = new int[count];
        for (int i = 0; i < count; i++) {
            theInts[i] = dis.readVarInt();
        }
    }

    public void process(GameContext context) {
        for (int i : theInts) {
            context.getSelf().getEnvironment().removeEntity(i);
        }
    }
}
//Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcutureRefactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture