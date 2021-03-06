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
public class CraftCocoa extends CraftAgeableBlock implements Cocoa {

    private final BlockFace facing;
    
    public CraftCocoa(JSONObject json, Material mat) {
        super(json, mat);
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public BlockFace getFacing() {
        return facing;
    }
        
}