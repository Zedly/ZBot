/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.block.data;

import org.json.simple.JSONObject;
import zedly.zbot.Material;
import zedly.zbot.block.data.BlockData;

/**
 *
 * @author Dennis
 */
public class CraftBlockData implements BlockData {

    private final Material mat;
    private JSONObject json;

    public CraftBlockData(Material mat) {
        this.mat = mat;
    }

    public CraftBlockData(JSONObject json, Material mat) {
        this.json = json;
        this.mat = mat;
    }

    @Override
    public Material getMaterial() {
        return mat;
    }

    @Override
    public String toString() {
        if (json != null) {
            return mat.toString() + json;
        }
        return mat.toString();
    }

    protected final String getEnumString(JSONObject json, String key) {
        return getString(json, key).toUpperCase();
    }

    protected final String getString(JSONObject json, String key) {
        return (String) json.get(key);
    }

    protected final int getInt(JSONObject json, String key) {
        return Integer.parseInt(getString(json, key));
    }

    protected final boolean getBoolean(JSONObject json, String key) {
        return getString(json, key).equalsIgnoreCase("true");
    }
}
