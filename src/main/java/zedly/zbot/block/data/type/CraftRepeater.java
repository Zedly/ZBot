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
public class CraftRepeater extends CraftBlockData implements Repeater {

    private final BlockFace facing;
    private final boolean locked, powered;
    private final int delay;
    
    public CraftRepeater(JSONObject json, Material mat) {
        super(json, mat);
        delay = getInt(json, "delay");
        locked = getBoolean(json, "locked");
        powered = getBoolean(json, "powered");
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public boolean isLocked() {
        return locked;
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