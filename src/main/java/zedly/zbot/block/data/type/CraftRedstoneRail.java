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
public class CraftRedstoneRail extends CraftBlockData implements RedstoneRail {

    private final Shape shape;
    private final boolean powered;
    
    public CraftRedstoneRail(JSONObject json, Material mat) {
        super(json, mat);
        powered = getBoolean(json, "powered");
        shape = Shape.valueOf(getEnumString(json, "shape"));
    }

    @Override
    public boolean isPowered() {
        return powered;
    }

    @Override
    public Shape getShape() {
        return shape;
    }
}