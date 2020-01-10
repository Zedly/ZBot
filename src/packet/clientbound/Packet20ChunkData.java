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
public class Packet20ChunkData implements ClientBoundPacket {

    private int chunkX;
    private int chunkZ;
    private boolean groundUpContinuous;
    private int primaryBitMask;
    private byte[] compressedData;
    private byte[] lightData;
    private NBTBase[] nbtTags;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        chunkX = dis.readInt();
        chunkZ = dis.readInt();
        groundUpContinuous = dis.readBoolean();
        primaryBitMask = dis.readVarInt();
        int compressedSize = dis.readVarInt();
        compressedData = new byte[compressedSize];
        dis.readFully(compressedData);
        int nbtTagCount = dis.readVarInt();
        nbtTags = new NBTBase[nbtTagCount];
        for(int i = 0; i < nbtTagCount; i++) {
            nbtTags[i] = dis.readNBT();
        }
    }

    @Override
    public void process(GameContext context) {
        CraftEnvironment environment = context.getSelf().getEnvironment();
        environment.loadChunkColumn(compressedData, chunkX, chunkZ, groundUpContinuous, primaryBitMask);
    }
}
//Refactored ancestor. Review data strcuture