/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.environment;

import java.util.HashMap;
import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;
import zedly.zbot.Location;
import zedly.zbot.Material;
import zedly.zbot.block.CraftTile;
import zedly.zbot.block.data.BlockData;
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
    private final HashMap<Long, NBTTagCompound> tiles = new HashMap<>();

    public CraftChunk(long[] packedBlockData) {
        this();
        int blockBitMask = (1 << GLOBAL_PALETTE_ENTROPY) - 1;
        int bitOffset = 0;

        int i = 0;
        try {
            for (i = 0; i < 4096; i++) {
                int blockField;
                if ((bitOffset % 64) > 0 && ((bitOffset - GLOBAL_PALETTE_ENTROPY) % 64) >= (bitOffset % 64)) {
                    // If this field spans two longs, snip them together
                    blockField = (int) ((packedBlockData[bitOffset / 64 - 1] << (bitOffset % 64))
                            | packedBlockData[bitOffset / 64] >> (64 - (bitOffset % 64))) & blockBitMask;
                } else {
                    blockField = (int) (packedBlockData[bitOffset / 64] >> (64 - (bitOffset % 64))) & blockBitMask;
                }

                dataIds[i] = blockField;
                bitOffset += GLOBAL_PALETTE_ENTROPY;
            }

            for (i = 0; i < 4096; i++) {
                skyLight[i] = 0;
                blockLight[i] = 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
        return getDataAt(x, y, z).getMaterial();
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
        tiles.remove(new Location(x, y, z).toLong());
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

    public boolean hasTileAt(int x, int y, int z) {
        return CraftChunk.this.hasTileAt(new Location(x, y, z));
    }

    public boolean hasTileAt(Location loc) {
        return tiles.containsKey(loc.toLong());
    }

    public NBTTagCompound getTileAt(Location loc) {
        return tiles.get(loc.toLong());
    }

    public CraftTile getTileAt(int x, int y, int z) {
        long chunkLong = new Location(x, y, z).toLong();
        if (tiles.containsKey(chunkLong)) {
            return CraftTile.forNbt(tiles.get(chunkLong));
        }
        return null;
    }

    public void setTileAt(Location loc, NBTTagCompound tile) {
        tiles.put(loc.toLong(), tile);
    }

    public void setTileAt(int x, int y, int z, NBTTagCompound tile) {
        setTileAt(new Location(x, y, z), tile);
    }

    public void removeTileAt(long chunkCoords) {
        tiles.remove(chunkCoords);
    }
}
