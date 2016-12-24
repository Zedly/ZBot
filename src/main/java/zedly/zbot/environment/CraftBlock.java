package zedly.zbot.environment;

import org.bukkit.Material;
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
    public int getTypeId() {
        return getChunk().getTypeIdAt(x, y, z);
    }
    
    public Material getType() {
        return Material.getMaterial(getTypeId());
    }
    
    @Override
    public int getData() {
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
        return mat == Material.WATER || mat == Material.STATIONARY_WATER
                || mat == Material.LAVA || mat == Material.STATIONARY_LAVA;
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
        return "{CraftBlock " + (isLoaded() ? "loaded" : "unloaded") + " " + getType() + ":" + getData() + " (" + getTypeId() + ") at " + x + " " + y + " " + z + "}";
    }
}
