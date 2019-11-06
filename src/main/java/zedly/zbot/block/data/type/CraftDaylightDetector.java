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
public class CraftDaylightDetector extends CraftBlockData implements DaylightDetector {

    private final int power;
    private final boolean inverted;
    
    public CraftDaylightDetector(JSONObject json, Material mat) {
        super(json, mat);
        inverted = getBoolean(json, "inverted");
        power = getInt(json, "power");
    }

    @Override
    public boolean isInverted() {
        return inverted;
    }

    @Override
    public int getPower() {
        return power;
    }
}