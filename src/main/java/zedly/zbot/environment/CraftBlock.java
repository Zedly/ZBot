package zedly.zbot.environment;

import zedly.zbot.Material;
import zedly.zbot.Location;

public class CraftBlock implements Block {

    private final CraftEnvironment env;
    private final int x, y, z;

    public CraftBlock(CraftEnvironment env, int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.env = env;
    }

    @Override
    public Location getLocation() {
        return new Location(x, y, z);
    }

    @Override
    public Material getType() {
        return getChunk().getDataAt(x, y, z).getType();
    }

    private int getDataId() {
        return getChunk().getDataIdAt(x, y, z);
    }

    @Override
    public BlockData getData() {
        return getChunk().getDataAt(x, y, z);
    }

    @Override
    public int getBlockLight() {
        return getChunk().getBlockLightAt(x, y, z);
    }

    @Override
    public int getSkyLight() {
        return getChunk().getSkyLightAt(x, y, z);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public boolean isSolid() {
        return getType().isSolid();
    }

    @Override
    public boolean isLiquid() {
        Material mat = getType();
        return mat == Material.WATER
                || mat == Material.LAVA;
    }

    @Override
    public Block getRelative(int dx, int dy, int dz) {
        return new CraftBlock(env, x + dx, y + dy, z + dz);
    }

    @Override
    public Block getRelative(BlockFace bf) {
        return getRelative(bf.getX(), bf.getY(), bf.getZ());
    }

    @Override
    public boolean isLoaded() {
        return env.isChunkLoaded(x, y, z);
    }

    private CraftChunk getChunk() {
        return env.getChunkAt(x, y, z);
    }

    @Override
    public String toString() {
        return "{CraftBlock " + (isLoaded() ? "loaded" : "unloaded") + " Type " + getDataId() +  " (" + getData() + ") at " + x + " " + y + " " + z + "}";
    }
}
