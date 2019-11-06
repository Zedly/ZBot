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
public class CraftFire extends CraftBlockData implements Fire {

    private final boolean east, north, west, south;
    private final int age;

    public CraftFire(JSONObject json, Material mat) {
        super(json, mat);
        east = getBoolean(json, "east");
        north = getBoolean(json, "north");
        west = getBoolean(json, "west");
        south = getBoolean(json, "south");
        age = getInt(json, "age");
    }

    @Override
    public boolean hasFace(BlockFace face) {
        switch (face) {
            case EAST:
                return east;
            case WEST:
                return west;
            case SOUTH:
                return south;
            case NORTH:
                return north;
            default:
                return false;
        }
    }

    @Override
    public int getAge() {
        return age;
    }
}
