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
public class CraftRedstoneWallTorch extends CraftBlockData implements RedstoneWallTorch {

    private final BlockFace facing;
    private final boolean lit;

    public CraftRedstoneWallTorch(JSONObject json, Material mat) {
        super(json, mat);
        lit = getBoolean(json, "lit");
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public BlockFace getFacing() {
        return facing;
    }

    @Override
    public boolean isLit() {
        return lit;
    }
}
