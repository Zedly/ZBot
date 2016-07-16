/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.api.event.block.BlockChangeEvent;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet10MultiBlockChange implements ClientBoundPacket {
    private int chunkX;
    private int chunkZ;
    private int dataSize;
    private int[] blockPos;
    private int[] blockIds;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        chunkX = dis.readInt();
        chunkZ = dis.readInt();
        int recordCount = dis.readVarInt();
        blockPos = new int[recordCount];
        blockIds = new int[recordCount];
        for (int i = 0; i < recordCount; i++) {
            blockPos[i] = ((dis.readUnsignedByte() << 8) + dis.readUnsignedByte());
            blockIds[i] = dis.readVarInt();
        }
    }
    
    @Override
    public void process(GameContext context) {
        for(int i = 0; i < blockPos.length; i++) {
            int x = chunkX * 16 + (blockPos[i] >> 12);
            int y = blockPos[i] & 0xFF;
            int z = chunkZ * 16 + (blockPos[i] >> 8) & 0xF;
            context.getMainThread().fireEvent(new BlockChangeEvent(new Location(x, y, z), blockIds[i]));
            context.getSelf().getEnvironment().setBlockAt(x, y, z, blockIds[i] >> 4, blockIds[i] & 0xF);
        }
    }
}
