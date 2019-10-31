/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.environment;

import org.json.simple.JSONObject;
import zedly.zbot.Material;
import zedly.zbot.environment.BlockData;

/**
 *
 * @author Dennis
 */
public class CraftBlockData implements BlockData {
    
    private final Material mat;
    
    public CraftBlockData(Material mat) {
        this.mat = mat;
    }
    
    public CraftBlockData(Material mat, JSONObject json) {
        this.mat = mat;
    }
    
    @Override
    public Material getType() {
        return mat;
    }
    
    @Override
    public String toString() {
        return mat.toString();
    }
}
