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
public class CraftJukebox extends CraftBlockData implements Jukebox {

    private final boolean record;
    
    public CraftJukebox(JSONObject json, Material mat) {
        super(json, mat);
        record = getBoolean(json, "has_record");
    }

    @Override
    public boolean hasRecord() {
        return record;
    }
}