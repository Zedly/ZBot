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
public class CraftLectern extends CraftBlockData implements Lectern {

    private final BlockFace facing;
    private final boolean book;
    private final boolean powered;
    
    public CraftLectern(JSONObject json, Material mat) {
        super(json, mat);
        book = getBoolean(json, "has_book");
        powered = getBoolean(json, "powered");
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }

    @Override
    public boolean hasBook() {
        return book;
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