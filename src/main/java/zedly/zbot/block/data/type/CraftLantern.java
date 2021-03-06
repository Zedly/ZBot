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
public class CraftLantern extends CraftBlockData implements Lantern {

    private final boolean hanging;
    
    public CraftLantern (JSONObject json, Material mat) {
        super(json, mat);
        hanging = getBoolean(json, "hanging");
    }

    @Override
    public boolean isHanging() {
        return hanging;
    }
}