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
public class CraftRedstoneWire extends CraftBlockData implements RedstoneWire {

    private final Connection east, north, west, south;
    private final int power;

    public CraftRedstoneWire(JSONObject json, Material mat) {
        super(json, mat);
        east = Connection.valueOf(getEnumString(json, "east"));
        north = Connection.valueOf(getEnumString(json, "north"));
        south = Connection.valueOf(getEnumString(json, "south"));
        west = Connection.valueOf(getEnumString(json, "west"));
        power = getInt(json, "power");
    }

    @Override
    public Connection getFace(BlockFace face) {
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
                return Connection.NONE;
        }
    }

    @Override
    public int getPower() {
        return power;
    }
}
