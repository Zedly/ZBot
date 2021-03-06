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
public class CraftCake extends CraftBlockData implements Cake {

    private final int bites;
    
    public CraftCake(JSONObject json, Material mat) {
        super(json, mat);
        bites = getInt(json, "bites");
    }

    @Override
    public int getBites() {
        return bites;
    }
        
}