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
public class CraftSeaPickle extends CraftBlockData implements SeaPickle {

    private final int pickles;
    private final boolean waterlogged;

    public CraftSeaPickle(JSONObject json, Material mat) {
        super(json, mat);
        waterlogged = getBoolean(json, "waterlogged");
        pickles = getInt(json, "pickles");
    }

    @Override
    public int getPickles() {
        return pickles;
    }

    @Override
    public boolean isWaterlogged() {
        return waterlogged;
    }
}
