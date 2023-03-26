/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.event.block.BlockChangeEvent;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.mappings.BlockDataIds;

/**
 *
 * @author Dennis
 */
public class Packet3FMultiBlockChange implements ClientBoundPacket {

    private int chunkX;
    private int chunkY;
    private int chunkZ;
    private boolean invalidateTrustEdges;
    private long[] blockUpdates;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        long sectionLong = dis.readLong();
        chunkX = (int) (sectionLong >> 42);
        chunkY = (int) (sectionLong << 44 >> 44);
        chunkZ = (int) (sectionLong << 22 >> 42);
        invalidateTrustEdges = dis.readBoolean();
        int recordCount = dis.readVarInt();
        blockUpdates = new long[recordCount];
        for (int i = 0; i < recordCount; i++) {
            blockUpdates[i] = dis.readVarLong();
        }
    }

    @Override
    public void process(GameContext context) {
        for (int i = 0; i < blockUpdates.length; i++) {
            int x = chunkX * 16 + (int) ((blockUpdates[i] >> 8) & 0xF);
            int y = (int) (blockUpdates[i] & 0xF);
            int z = chunkZ * 16 + (int) ((blockUpdates[i] >> 4) & 0xF);
            int blockId = (int)(blockUpdates[i] >>> 12);
            context.getMainThread().fireEvent(new BlockChangeEvent(new Location(x, y, z), BlockDataIds.fromId(blockId)));
            context.getSelf().getEnvironment().setBlockAt(x, y, z, blockId);
        }
    }
}
//Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcutureRefactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture