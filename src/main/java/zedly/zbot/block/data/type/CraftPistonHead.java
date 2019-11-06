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
public class CraftPistonHead extends CraftBlockData implements PistonHead {

    private final BlockFace facing;
    private final Type type;
    private final boolean isShort;

    public CraftPistonHead(JSONObject json, Material mat) {
        super(json, mat);
        isShort = getBoolean(json, "short");
        type = Type.valueOf(getEnumString(json, "type"));
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public boolean isShort() {
        return isShort;
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
