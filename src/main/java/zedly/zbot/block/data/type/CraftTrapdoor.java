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
public class CraftTrapdoor extends CraftBlockData implements Trapdoor {

    private final BlockFace facing;
    private final Half half;
    private final boolean open;
    private final boolean powered;
    private final boolean waterlogged;

    public CraftTrapdoor(JSONObject json, Material mat) {
        super(json, mat);
        powered = getBoolean(json, "powered");
        open = getBoolean(json, "open");
        waterlogged = getBoolean(json, "waterlogged");
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
        half = Half.safeValueOf(getEnumString(json, "half"));
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

    @Override
    public boolean isWaterlogged() {
        return waterlogged;
    }
}
