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
public class CraftComparator extends CraftBlockData implements Comparator {

    private final Mode mode;
    private final BlockFace facing;
    private final boolean powered;
    
    public CraftComparator(JSONObject json, Material mat) {
        super(json, mat);
        powered = getBoolean(json, "powered");
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
        mode = Mode.valueOf(getEnumString(json, "mode"));
    }

    @Override
    public Mode getMode() {
        return mode;
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
