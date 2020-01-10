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
public class CraftTileCommandBlock extends CraftTile implements TileCommandBlock {

    private String command, lastOutput;
    
    public CraftTileCommandBlock(NBTTagCompound nbt) {
        super(nbt);
        command = nbt.getString("command");
        lastOutput = nbt.getString("last_output");
    }
    
    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getLastOutput() {
        return lastOutput;
    }
    
    public String getTileDetails() {
        return "CommandBlock " + getCommand() + ", LastOutput: " + getLastOutput();
    }
    
}
