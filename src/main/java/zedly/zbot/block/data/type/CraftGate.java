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
public class CraftGate extends CraftBlockData implements Gate {

    private final BlockFace facing;
    private final boolean open;
    private final boolean powered;
    private final boolean inWall;
    
    public CraftGate(JSONObject json, Material mat) {
        super(json, mat);
        open = getBoolean(json, "open");
        powered = getBoolean(json, "powered");
        inWall = getBoolean(json, "in_wall");
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public boolean isInWall() {
        return inWall;
    }

    @Override
    public BlockFace getFacing() {
        return facing;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public boolean isPowered() {
        return powered;
    }
}