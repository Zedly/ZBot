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
public class CraftCampfire extends CraftBlockData implements Campfire {

    private final boolean signal, lit, waterlogged;
    
    public CraftCampfire(JSONObject json, Material mat) {
        super(json, mat);
        signal = getBoolean(json, "signal_fire");
        lit = getBoolean(json, "lit");
        waterlogged = getBoolean(json, "waterlogged");
    }

    @Override
    public boolean isSignalFire() {
        return signal;
    }    

    @Override
    public boolean isLit() {
        return lit;
    }

    @Override
    public boolean isWaterlogged() {
        return waterlogged;
    }
}