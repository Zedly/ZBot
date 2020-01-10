/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.block;

import net.minecraft.server.NBTTagCompound;
import zedly.zbot.Material;

/**
 *
 * @author Dennis
 */
public class CraftTileBed extends CraftTile {
    
    public CraftTileBed(NBTTagCompound nbt) {
        super(nbt);
    }
    
    @Override
    public String getTileDetails() {
        return "Bed";
    }
    
}
