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
public class CraftChest extends CraftBlockData implements Chest {

    private final Type type;
    private final BlockFace facing;
    private final boolean waterlogged;

    public CraftChest(JSONObject json, Material mat) {
        super(json, mat);
        type = Type.valueOf(getEnumString(json, "type"));
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
        waterlogged = getBoolean(json, "waterlogged");
    }

    @Override
    public Type getType() {
        return type;
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