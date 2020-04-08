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
import zedly.zbot.block.data.AgeableBlock;

/**
 *
 * @author Dennis
 */
public class CraftAgeableBlock extends CraftBlockData implements AgeableBlock {

    private final int age;

    public CraftAgeableBlock(JSONObject json, Material mat) {
        super(json, mat);
        age = getInt(json, "age");
    }

    @Override
    public int getAge() {
        return age;
    }

}
