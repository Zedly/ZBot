/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.environment;

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
    
    public int getTypeIdAt(int x, int y, int z) {
        return blockIds[toArrayIndex(x, y, z)];
    }

    public int getDataAt(int x, int y, int z) {
        return blockData[toArrayIndex(x, y, z)];
    }

    public int getBlockLightAt(int x, int y, int z) {
        return blockLight[toArrayIndex(x, y, z)];
    }

    public int getSkyLightAt(int x, int y, int z) {
        return skyLight[toArrayIndex(x, y, z)];
    }
    
    public void setBlockAt(int x, int y, int z, int typeId, int blockData) {
        int arrayIndex = toArrayIndex(x, y, z);
        blockIds[arrayIndex] = (short) typeId;
        this.blockData[arrayIndex] = (byte) blockData;
    }

    private int toArrayIndex(int x, int y, int z) {
        int localX, localY, localZ;
        if (x < 0) {
            localX = 15 + ((x + 1) % 16);
        } else {
            localX = x % 16;
        }
        if (y < 0) {
            localY= 15 + ((y + 1) % 16);
        } else {
            localY = y % 16;
        }
        if (z < 0) {
            localZ = 15 + ((z + 1) % 16);
        } else {
            localZ = z % 16;
        }
        return 256 * localY + 16 * localZ + localX;
    }
    
}
