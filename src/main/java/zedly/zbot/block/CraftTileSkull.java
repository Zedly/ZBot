/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.block;

import java.util.UUID;
import net.minecraft.server.NBTTagCompound;
import zedly.zbot.EntityType;

/**
 *
 * @author Dennis
 */
public class CraftTileSkull extends CraftTile implements TileSkull {
    
    private UUID owner;
    private String ownerName;
    
    public CraftTileSkull(NBTTagCompound nbt) {
        super(nbt);
        owner = UUID.fromString(nbt.getCompoundTag("Owner").getString("Id"));
        ownerName = nbt.getCompoundTag("Owner").getString("Name");
    }
    
    public EntityType getMobType() {
        return null;
    }
    
    public UUID getOwner() {
        return owner;
    }

    public String getOwnerName() {
        return ownerName;
    }
    
    public String getTileDetails() {
        return "Skull of " + getOwnerName();
    }
    
}
