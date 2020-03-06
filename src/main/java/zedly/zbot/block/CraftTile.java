/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.block;

import net.minecraft.server.NBTTagCompound;
import zedly.zbot.Location;
import zedly.zbot.Material;

/**
 *
 * @author Dennis
 */
public class CraftTile implements Tile {

    String canonicalType;
    private Location loc;
    private Material type;
    private NBTTagCompound nbt;

    public CraftTile(NBTTagCompound nbt) {
        this.nbt = nbt;
        this.canonicalType = nbt.getString("id");
        this.loc = new Location(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
    }

    public String getCanonicalType() {
        return canonicalType;
    }

    @Override
    public Location getLocation() {
        return loc;
    }

    public NBTTagCompound getNBT() {
        return nbt;
    }

    // String IDs are so annoying
    public static CraftTile forNbt(NBTTagCompound nbt) {
        switch (nbt.getString("id")) {
            case "minecraft:sign":
            case "sign":
                return new CraftTileSign(nbt);
            case "minecraft:mob_spawner":
            case "mob_spawner":
                return new CraftTileSpawner(nbt);
            case "minecraft:bed":
            case "bed":
                return new CraftTileBed(nbt);
            case "minecraft:skull":
            case "skull":
                return new CraftTileSkull(nbt);
            case "minecraft:command_block":
            case "command_block":
                return new CraftTileCommandBlock(nbt);    
        }
        return null;
    }

    @Override
    public final String toString() {
        return "{CraftTile " + getTileDetails() + " at " + loc + "}";
    }

    protected String getTileDetails() {
        return "generic " + getCanonicalType() + " NBT: " + getNBT();
    }

}
