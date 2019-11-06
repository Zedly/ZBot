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
public class CraftTurtleEgg extends CraftBlockData implements TurtleEgg {

    private final int eggs;
    private final int hatch;

    public CraftTurtleEgg(JSONObject json, Material mat) {
        super(json, mat);
        eggs = getInt(json, "eggs");
        hatch = getInt(json, "hatch");
    }

    @Override
    public int getEggs() {
        return eggs;
    }

    @Override
    public int getHatch() {
        return hatch;
    }
}
