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
public class CraftDispenser extends CraftBlockData implements Dispenser {

    private final BlockFace facing;
    private final boolean triggered;
    
    public CraftDispenser(JSONObject json, Material mat) {
        super(json, mat);
        triggered = getBoolean(json, "triggered");
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public boolean isTriggered() {
        return triggered;
    }

    @Override
    public BlockFace getFacing() {
        return facing;
    }
}