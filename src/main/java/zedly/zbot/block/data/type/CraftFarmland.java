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
public class CraftFarmland extends CraftBlockData implements Farmland {

    private final int moisture;
    
    public CraftFarmland(JSONObject json, Material mat) {
        super(json, mat);
        moisture = getInt(json, "moisture");
    }

    @Override
    public int getMoisture() {
        return moisture;
    }
}