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
public class CraftStairs extends CraftBlockData implements Stairs {

    private final Shape shape;
    private final Half half;
    private final BlockFace facing;
    private final boolean waterlogged;
    
    public CraftStairs(JSONObject json, Material mat) {
        super(json, mat);
        waterlogged = getBoolean(json, "waterlogged");
        shape = Shape.valueOf(getEnumString(json, "shape"));
        half = Half.safeValueOf(getEnumString(json, "half"));
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public Half getHalf() {
        return half;
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