package zedly.zbot.block;

import zedly.zbot.block.Block;
import zedly.zbot.Location;

public class CraftBlock implements Block {

    private final int x, y, z;
    private final int typeId, blockData, blockLight, skyLight;

    public CraftBlock(int x, int y, int z, int typeId, int blockData, int blockLight, int skyLight) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.typeId = typeId;
        this.blockData = blockData;
        this.blockLight = blockLight;
        this.skyLight = skyLight;
    }

    @Override
    public Location getLocation() {
        return new Location(x, y, z);
    }
    
    @Override
    public int getTypeId() {
        return typeId;
    }
    
    @Override
    public int getData() {
        return blockData;
    }
    
    @Override
    public int getBlockLight() {
        return blockLight;
    }
    
    @Override
    public int getSkyLight() {
        return skyLight;
    }
}
