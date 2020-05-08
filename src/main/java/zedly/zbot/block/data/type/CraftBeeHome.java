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
public class CraftBeeHome extends CraftBlockData implements BeeHome {

    private final int honeyLevel;
    private final BlockFace direction;
    
    public CraftBeeHome(JSONObject json, Material mat) {
        super(json, mat);
        honeyLevel = getInt(json, "honey_level");
        direction = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public int getHoneyLevel() {
        return honeyLevel;
    }

    @Override
    public BlockFace getFacing() {
        return direction;
    }
    
}
