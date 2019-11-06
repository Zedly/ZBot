/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.environment.CraftEnvironment;
import net.minecraft.server.NBTBase;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet21ChunkData implements ClientBoundPacket {

    private int chunkX;
    private int chunkZ;
    private boolean groundUpContinuous;
    private NBTBase heightmap;
    private int primaryBitMask;
    private byte[] blockData;
    private byte[] lightData;
    private NBTBase[] blockEntities;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        chunkX = dis.readInt();
        chunkZ = dis.readInt();
        groundUpContinuous = dis.readBoolean();
        primaryBitMask = dis.readVarInt();
        heightmap = dis.readNBT();
        int size = dis.readVarInt();
        blockData = new byte[size];
        dis.readFully(blockData);
        int blockEntityCount = dis.readVarInt();
        blockEntities = new NBTBase[blockEntityCount];
        for(int i = 0; i < blockEntityCount; i++) {
            blockEntities[i] = dis.readNBT();
        }
    }

    @Override
    public void process(GameContext context) {
        CraftEnvironment environment = context.getSelf().getEnvironment();
        environment.loadChunkColumn(blockData, chunkX, chunkZ, groundUpContinuous, primaryBitMask, blockEntities);
    }
}
//Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture