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
public class CraftLadder extends CraftBlockData implements Ladder {

    private final BlockFace facing;
    private final boolean waterlogged;
    
    public CraftLadder(JSONObject json, Material mat) {
        super(json, mat);
        waterlogged = getBoolean(json, "waterlogged");
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public BlockFace getFacing() {
        return facing;
    }

    @Override
    public boolean isWaterlogged() {
        return waterlogged;
    }
}