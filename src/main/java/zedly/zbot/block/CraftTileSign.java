/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.block;

import net.minecraft.server.NBTTagCompound;
import zedly.zbot.DyeColor;
import zedly.zbot.Util;

/**
 *
 * @author Dennis
 */
public class CraftTileSign extends CraftTile implements TileSign {
    
    private String[] lines = new String[4];
    private DyeColor color;
    
    public CraftTileSign(NBTTagCompound nbt) {
        super(nbt);
        lines[0] = Util.interpretJson(nbt.getString("Text1"));
        lines[1] = Util.interpretJson(nbt.getString("Text2"));
        lines[2] = Util.interpretJson(nbt.getString("Text3"));
        lines[3] = Util.interpretJson(nbt.getString("Text4"));
        color = DyeColor.valueOf(nbt.getString("Color").toUpperCase());
    }
    
    public String getLine(int line) {
        return lines[line];
    }
    
    public String getTileDetails() {
        return "Sign \"" + getLine(0) + "\" ," + getLine(1) + "\", \"" + getLine(2) + "\", \"" + getLine(3) + "\"";
    }
    
}
