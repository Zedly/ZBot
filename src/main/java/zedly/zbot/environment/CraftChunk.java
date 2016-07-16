/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.environment;

import zedly.zbot.block.CraftBlock;

/**
 *
 * @author Dennis
 */
public class CraftChunk implements Chunk {

    private final short[] blockIds;
    private final byte[] blockData;
    private final byte[] blockLight;
    private final byte[] skyLight;

    public CraftChunk(long[] packedBlockData, byte[] packedBlockLight, byte[] packedSkyLight) {
        this();
        
        int bitOffset = 13;
        for (int i = 0; i < 4096; i++) {
            int blockField;
            if ((bitOffset % 64) > 12) {
                blockField = (int) (packedBlockData[bitOffset / 64] >> (64 - (bitOffset % 64))) & 0x1FFF;
            } else {
                blockField = (int) ((packedBlockData[bitOffset / 64 - 1] << (bitOffset % 64))
                        | packedBlockData[bitOffset / 64] >> (64 - (bitOffset % 64))) & 0x1FFF;
            }
            blockIds[i] = (short) (blockField >> 4);
            blockData[i] = (byte) (blockField & 0xF);
            if (i % 2 == 0) {
                blockLight[i] = (byte) (packedBlockLight[i / 2] >> 4);
            } else {
                blockLight[i] = (byte) (packedBlockLight[i / 2] & 0xF);
            }
            bitOffset += 13;
        }        

        if (packedSkyLight != null) {
            for (int i = 0; i < 4096; i++) {
                if (i % 2 == 0) {
                    skyLight[i] = (byte) (packedSkyLight[i / 2] >> 4);
                } else {
                    skyLight[i] = (byte) (packedSkyLight[i / 2] & 0xF);
                }
            }
        }
    }

    public CraftChunk(long[] packedBlockData, int bitsPerBlock, int[] palette, byte[] packedBlockLight, byte[] packedSkyLight) {
        this();
        int bitOffset = 0;
        
        for (int i = 0; i < 4096; i++) {
            int blockField;
            if (64 - (bitOffset % 64) >= bitsPerBlock) {
                blockField = (int) (packedBlockData[bitOffset / 64] >>> (bitOffset % 64)) & ~(-1 << bitsPerBlock);
            } else {
                blockField = (int) ((packedBlockData[bitOffset / 64] >>> (bitOffset % 64))
                        | packedBlockData[bitOffset / 64 + 1] << (64 - (bitOffset % 64))) & ~(-1 << bitsPerBlock);
            }
            blockField = palette[blockField];
            blockIds[i] = (short) (blockField >> 4);
            blockData[i] = (byte) (blockField & 0xF);
            if (i % 2 == 0) {
                blockLight[i] = (byte) (packedBlockLight[i / 2] >> 4);
            } else {
                blockLight[i] = (byte) (packedBlockLight[i / 2] & 0xF);
            }
            bitOffset += bitsPerBlock;
        }        

        if (packedSkyLight != null) {
            for (int i = 0; i < 4096; i++) {
                if (i % 2 == 0) {
                    skyLight[i] = (byte) (packedSkyLight[i / 2] >> 4);
                } else {
                    skyLight[i] = (byte) (packedSkyLight[i / 2] & 0xF);
                }
            }
        }
    }

    public CraftChunk() {
        this.blockIds = new short[4096];
        this.blockData = new byte[4096];
        this.skyLight = new byte[4096];
        this.blockLight = new byte[4096];
    }
    
    public CraftBlock getBlockAt(int x, int y, int z) {
        int id = blockIds[256 * y + 16 * z + x];
        int data = blockData[256 * y + 16 * z + x];
        int slight = skyLight[256 * y + 16 * z + x];
        int blight = blockLight[256 * y + 16 * z + x];
        return new CraftBlock(x, y, z, id, data, blight, slight);
    }
    
    public void setBlockAt(int x, int y, int z, int typeId, int blockData) {
        blockIds[256 * y + 16 * z + x] = (short) typeId;
        this.blockData[256 * y + 16 * z + x] = (byte) blockData;
    }

}
