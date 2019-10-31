/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.environment;

import zedly.zbot.Material;
import zedly.zbot.network.mappings.BlockDataIds;

/**
 *
 * @author Dennis
 */
public class CraftChunk implements Chunk {

    private static final int GLOBAL_PALETTE_ENTROPY = 14;

    private final int[] dataIds;
    private final byte[] blockLight;
    private final byte[] skyLight;

    public CraftChunk(long[] packedBlockData) {
        this();
        int blockBitMask = (1 << GLOBAL_PALETTE_ENTROPY) - 1;
        int bitOffset = GLOBAL_PALETTE_ENTROPY;

        for (int i = 0; i < 4096; i++) {
            int blockField;
            if ((bitOffset % 64) > bitOffset - 1) {
                blockField = (int) (packedBlockData[bitOffset / 64] >> (64 - (bitOffset % 64))) & blockBitMask;
            } else {
                blockField = (int) ((packedBlockData[bitOffset / 64 - 1] << (bitOffset % 64))
                        | packedBlockData[bitOffset / 64] >> (64 - (bitOffset % 64))) & blockBitMask;
            }

            dataIds[i] = blockField;
            bitOffset += GLOBAL_PALETTE_ENTROPY;
        }

        for (int i = 0; i < 4096; i++) {
            skyLight[i] = 0;
            blockLight[i] = 0;
        }
    }

    public CraftChunk(long[] packedBlockData, int bitsPerBlock, int[] palette) {
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
            dataIds[i] = palette[blockField];
            bitOffset += bitsPerBlock;
        }

        for (int i = 0; i < 4096; i++) {
            skyLight[i] = 0;
            blockLight[i] = 0;
        }
    }

    public CraftChunk() {
        this.dataIds = new int[4096];
        this.skyLight = new byte[4096];
        this.blockLight = new byte[4096];
    }

    public int getDataIdAt(int x, int y, int z) {
        return dataIds[toArrayIndex(x, y, z)];
    }

    public BlockData getDataAt(int x, int y, int z) {
        return BlockDataIds.fromId(getDataIdAt(x, y, z));
    }

    public Material getTypeAt(int x, int y, int z) {
        return getDataAt(x, y, z).getType();
    }

    public int getBlockLightAt(int x, int y, int z) {
        return blockLight[toArrayIndex(x, y, z)];
    }

    public int getSkyLightAt(int x, int y, int z) {
        return skyLight[toArrayIndex(x, y, z)];
    }

    public void setBlockAt(int x, int y, int z, int dataId) {
        int arrayIndex = toArrayIndex(x, y, z);
        dataIds[arrayIndex] = (short) dataId;
    }

    private int toArrayIndex(int x, int y, int z) {
        int localX, localY, localZ;
        if (x < 0) {
            localX = 15 + ((x + 1) % 16);
        } else {
            localX = x % 16;
        }
        if (y < 0) {
            localY = 15 + ((y + 1) % 16);
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
