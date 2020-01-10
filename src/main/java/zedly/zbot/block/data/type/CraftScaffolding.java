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
public class CraftScaffolding extends CraftBlockData implements Scaffolding {

    private final boolean waterlogged, bottom;
    private final int distance;

    public CraftScaffolding(JSONObject json, Material mat) {
        super(json, mat);
        waterlogged = getBoolean(json, "waterlogged");
        bottom = getBoolean(json, "bottom");
        distance = getInt(json, "distance");
    }

    @Override
    public boolean isBottom() {
        return bottom;
    }

    @Override
    public int getDistance() {
        return distance;
    }

    @Override
    public boolean isWaterlogged() {
        return waterlogged;
    }
}
