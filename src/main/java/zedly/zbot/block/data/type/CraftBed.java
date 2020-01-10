/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.block.data.type;

import java.util.Set;
import org.json.simple.JSONObject;
import zedly.zbot.BlockFace;
import zedly.zbot.Material;
import zedly.zbot.block.data.CraftBlockData;

/**
 *
 * @author Dennis
 */
public class CraftBed extends CraftBlockData implements Bed {

    private final Part part;
    private final BlockFace facing;
    
    public CraftBed(JSONObject json, Material mat) {
        super(json, mat);
        part = Part.valueOf(getEnumString(json, "part"));
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public Part getPart() {
        return part;
    }

    @Override
    public BlockFace getFacing() {
        return facing;
    }
}
