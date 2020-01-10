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
public class CraftSwitch extends CraftBlockData implements Switch {

    private final BlockFace facing;
    private final Face face;
    private final boolean powered;
    
    public CraftSwitch(JSONObject json, Material mat) {
        super(json, mat);
        powered = getBoolean(json, "powered");
        face = Face.valueOf(getEnumString(json, "face"));
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public Face getFace() {
        return face;
    }

    @Override
    public BlockFace getFacing() {
        return facing;
    }

    @Override
    public boolean isPowered() {
        return powered;
    }
}