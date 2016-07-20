/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.UnloadChunkEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * @author Dennis
 */
public class Packet1DUnloadChunk implements ClientBoundPacket {

    private int chunkX;
    private int chunkZ;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        chunkX = dis.readInt();
        chunkZ = dis.readInt();
    }

    @Override
    public void process(GameContext context) {
        context.getMainThread().fireEvent(new UnloadChunkEvent(chunkX, chunkZ));
    }
}
