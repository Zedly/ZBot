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
public class CraftTechnicalPiston extends CraftBlockData implements TechnicalPiston {

    private final BlockFace facing;
    private final Type type;

    public CraftTechnicalPiston(JSONObject json, Material mat) {
        super(json, mat);
        type = Type.valueOf(getEnumString(json, "type"));
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public BlockFace getFacing() {
        return facing;
    }
}
