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
public class CraftPiston extends CraftBlockData implements Piston {

    private final BlockFace facing;
    private final boolean extended;
    
    public CraftPiston(JSONObject json, Material mat) {
        super(json, mat);
        extended = getBoolean(json, "extended");
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public boolean isExtended() {
        return extended;
    }

    @Override
    public BlockFace getFacing() {
        return facing;
    }
}