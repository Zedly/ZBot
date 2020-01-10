/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.block;

import net.minecraft.server.NBTTagCompound;
import zedly.zbot.EntityType;

/**
 *
 * @author Dennis
 */
public class CraftTileSpawner extends CraftTile implements TileSpawner {

    private EntityType type;
    
    public CraftTileSpawner(NBTTagCompound nbt) {
        super(nbt);
        type = forString(nbt.getCompoundTag("SpawnData").getString("id"));
    }
    
    @Override
    public EntityType getSpawnerType() {
        return type;
    }
    
    @Override
    public String getTileDetails() {
        return "Spawner " + getSpawnerType();
    }
    
    private static EntityType forString(String canonical) {
        return EntityType.valueOf(canonical.substring(canonical.indexOf(":") + 1).toUpperCase());
    }
    
}
