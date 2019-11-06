/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.block.data.type;

import org.json.simple.JSONObject;
import zedly.zbot.BlockFace;
import static zedly.zbot.BlockFace.*;
import zedly.zbot.Material;
import zedly.zbot.block.data.CraftBlockData;

/**
 *
 * @author Dennis
 */
public class CraftSign extends CraftBlockData implements Sign {

    private static final BlockFace[] ORDINAL_DIRECTIONS = {
        SOUTH, SOUTH_SOUTH_WEST, SOUTH_WEST, WEST_SOUTH_WEST,
        WEST, WEST_NORTH_WEST, NORTH_WEST, NORTH_NORTH_WEST,
        NORTH, NORTH_NORTH_EAST, NORTH_EAST, EAST_NORTH_EAST,
        EAST, EAST_SOUTH_EAST, SOUTH_EAST, SOUTH_SOUTH_EAST

    };
    private final BlockFace facing;
    private final boolean waterlogged;

    public CraftSign(JSONObject json, Material mat) {
        super(json, mat);
        waterlogged = getBoolean(json, "waterlogged");
        facing = ORDINAL_DIRECTIONS[getInt(json, "rotation")];
    }

    @Override
    public BlockFace getRotation() {
        return facing;
    }

    @Override
    public boolean isWaterlogged() {
        return waterlogged;
    }

}
