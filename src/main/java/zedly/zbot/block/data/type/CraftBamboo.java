/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.block.data.type;

import org.json.simple.JSONObject;
import zedly.zbot.Material;
import zedly.zbot.block.data.CraftBlockData;

/**
 *
 * @author Dennis
 */
public class CraftBamboo extends CraftBlockData implements Bamboo {

    private final Leaves leaves;
    private final int age;
    private final int stage;
    
    public CraftBamboo(JSONObject json, Material mat) {
        super(json, mat);
        leaves = Leaves.valueOf(getEnumString(json, "leaves"));
        age = getInt(json, "age");
        stage = getInt(json, "stage");
    }
    
    @Override
    public Leaves getLeaves() {
        return leaves;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public int getStage() {
        return stage;
    }
}
