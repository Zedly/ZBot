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
    public synchronized Location getLocation() {
        return new Location(x, y, z);
    }
    
    @Override
    public synchronized int getTypeId() {
        return typeId;
    }
    
    @Override
    public synchronized int getData() {
        return blockData;
    }
    
    @Override
    public synchronized int getBlockLight() {
        return blockLight;
    }
    
    @Override
    public synchronized int getSkyLight() {
        return skyLight;
    }
}
