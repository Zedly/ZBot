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
public class CraftBell extends CraftBlockData implements Bell {

    private final Attachment attachment;
    private final BlockFace facing;
    
    public CraftBell(JSONObject json, Material mat) {
        super(json, mat);
        attachment = Attachment.valueOf(getEnumString(json, "attachment"));
        facing = BlockFace.valueOf(getEnumString(json, "facing"));
    }
    
    @Override
    public Attachment getAttachment() {
        return attachment;
    }

    @Override
    public BlockFace getFacing() {
        return facing;
    }
    
}
