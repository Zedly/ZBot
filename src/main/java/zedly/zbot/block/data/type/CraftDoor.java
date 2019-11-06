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
public class CraftDoor extends CraftBlockData implements Door {

    private final BlockFace facing;
    private final Hinge hinge;
    private final Half half;
    private final boolean open;
    private final boolean powered;
    
    public CraftDoor(JSONObject json, Material mat) {
        super(json, mat);
        open = getBoolean(json, "open");
        powered = getBoolean(json, "powered");
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
        hinge = Hinge.valueOf(getEnumString(json, "hinge"));
        half = Half.safeValueOf(getEnumString(json, "half"));
    }

    @Override
    public Hinge getHinge() {
        return hinge;
    }

    @Override
    public Half getHalf() {
        return half;
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