/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.ArrayList;
import zedly.zbot.GameContext;
import zedly.zbot.environment.CraftEnvironment;
import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagInt;
import zedly.zbot.BitSet;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet22ChunkData implements ClientBoundPacket {

    private int chunkX;
    private int chunkZ;
    private NBTBase heightmap;
    private byte[] blockData;
    private NBTTagCompound[] blockEntities;
    private boolean trustEdges;
    private BitSet skyLightMask;
    private BitSet blockLightMask;
    private BitSet emptySkyLightMask;
    private BitSet emptyBlockLightMask;
    private ArrayList<byte[]> skyLightArrays = new ArrayList<>();
    private ArrayList<byte[]> blockLightArrays = new ArrayList<>();

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        chunkX = dis.readInt();
        chunkZ = dis.readInt();
        heightmap = dis.readNBT();
        int size = dis.readVarInt();
        blockData = new byte[size];
        dis.readFully(blockData);
        int blockEntityCount = dis.readVarInt();
        blockEntities = new NBTTagCompound[blockEntityCount];

        for (int i = 0; i < blockEntityCount; i++) {
            int packedXZ = dis.readByte();
            int Y = dis.readShort();
            int entityType = dis.readVarInt();
            blockEntities[i] = (NBTTagCompound) dis.readNBT();
            blockEntities[i].setInteger("x", (packedXZ >> 4) & 0xF);
            blockEntities[i].setInteger("z", packedXZ & 0xF);
            blockEntities[i].setInteger("y", Y);
        }
        trustEdges = dis.readBoolean();

        skyLightMask = dis.readBitSet();
        blockLightMask = dis.readBitSet();
        emptySkyLightMask = dis.readBitSet();
        emptyBlockLightMask = dis.readBitSet();

        int skyLightArrayCount = dis.readVarInt();
        for (int i = 0; i < skyLightArrayCount; i++) {
            int length = dis.readVarInt();
            byte[] skyLightData = new byte[length];
            dis.readFully(skyLightData);
            skyLightArrays.add(skyLightData);
        }

        int blockLightArrayCount = dis.readVarInt();
        for (int i = 0; i < blockLightArrayCount; i++) {
            int length = dis.readVarInt();
            byte[] blockLightData = new byte[length];
            dis.readFully(blockLightData);
            skyLightArrays.add(blockLightData);
        }
    }

    @Override
    public void process(GameContext context) {
        CraftEnvironment environment = context.getSelf().getEnvironment();
        environment.loadChunkColumn(blockData, chunkX, chunkZ, skyLightMask, blockLightMask, emptySkyLightMask, emptyBlockLightMask, skyLightArrays, blockLightArrays, blockEntities);
    }
}
//Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture
