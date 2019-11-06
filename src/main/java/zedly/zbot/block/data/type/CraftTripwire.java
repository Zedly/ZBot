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
public class CraftTripwire extends CraftBlockData implements Tripwire {

    private final boolean east, north, west, south, disarmed, powered, attached;

    public CraftTripwire(JSONObject json, Material mat) {
        super(json, mat);
        east = getBoolean(json, "east");
        north = getBoolean(json, "north");
        west = getBoolean(json, "west");
        south = getBoolean(json, "south");
        disarmed = getBoolean(json, "disarmed");
        powered = getBoolean(json, "powered");
        attached = getBoolean(json, "attached");
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
    public boolean isDisarmed() {
        return disarmed;
    }

    @Override
    public boolean isAttached() {
        return attached;
    }

    @Override
    public boolean isPowered() {
        return powered;
    }
}