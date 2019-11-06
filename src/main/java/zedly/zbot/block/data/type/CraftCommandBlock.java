/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.block.data.type;

import org.json.simple.JSONObject;
import zedly.zbot.BlockFace;
import zedly.zbot.Material;
import zedly.zbot.block.data.CraftBlockData;

/**
 *
 * @author Dennis
 */
public class CraftCommandBlock extends CraftBlockData implements CommandBlock {

    boolean conditional;
    BlockFace facing;
    
    public CraftCommandBlock(JSONObject json, Material mat) {
        super(json, mat);
        conditional = getBoolean(json, "conditional");
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public boolean isConditional() {
        return conditional;
    }

    @Override
    public BlockFace getFacing() {
        return facing;
    }
        
}