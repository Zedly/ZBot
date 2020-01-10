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
public class CraftStructureBlock extends CraftBlockData implements StructureBlock {

    private final Mode mode;

    public CraftStructureBlock(JSONObject json, Material mat) {
        super(json, mat);
        mode = Mode.valueOf(getEnumString(json, "mode"));
    }

    @Override
    public Mode getMode() {
        return mode;
    }
}
