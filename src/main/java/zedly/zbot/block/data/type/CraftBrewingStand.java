/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.block.data.type;

import java.util.HashSet;
import java.util.Set;
import org.json.simple.JSONObject;
import zedly.zbot.BlockFace;
import zedly.zbot.Material;
import zedly.zbot.block.data.CraftBlockData;

/**
 *
 * @author Dennis
 */
public class CraftBrewingStand extends CraftBlockData implements BrewingStand {

    boolean[] bottles = {false, false, false};

    public CraftBrewingStand(JSONObject json, Material mat) {
        super(json, mat);
        bottles[0] = getBoolean(json, ("has_bottle_0"));
        bottles[1] = getBoolean(json, ("has_bottle_1"));
        bottles[2] = getBoolean(json, ("has_bottle_2"));
    }

    @Override
    public boolean hasBottle(int bottle) {
        return bottle > 0 && bottle < 3 && bottles[bottle];
    }

    @Override
    public Set<Integer> getBottles() {
        Set<Integer> hasBottles = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            if (bottles[i]) {
                hasBottles.add(i);
            }
        }
        return hasBottles;
    }

}
