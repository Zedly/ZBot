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
public class CraftSlab extends CraftBlockData implements Slab {

    private final Type type;
    private final boolean waterlogged;
    
    public CraftSlab(JSONObject json, Material mat) {
        super(json, mat);
        waterlogged = getBoolean(json, "waterlogged");
        type = Type.valueOf(getEnumString(json, "type"));
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public boolean isWaterlogged() {
        return waterlogged;
    }
}