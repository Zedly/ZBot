/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.mappings;

import com.google.common.io.Resources;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.io.Charsets;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import zedly.zbot.Material;
import zedly.zbot.block.data.BlockData;
import zedly.zbot.block.data.CraftBlockData;
import zedly.zbot.block.data.type.*;

/**
 *
 * @author Dennis
 */
public class BlockDataIds {

    private static final HashMap<Integer, BlockData> blockDataMap = new HashMap<>();
    private static final HashMap<Class<? extends BlockData>, Class<? extends CraftBlockData>> blockDataClassMap = new HashMap<>();

    public static BlockData fromId(int blockDataId) {
        return blockDataMap.getOrDefault(blockDataId, new CraftBlockData(Material.AIR));
    }

    private static void parseAllInstances() {
        try {
            URL url = Resources.getResource("blocks.json");
            String s_json = Resources.toString(url, Charsets.UTF_8);
            JSONParser parser = new JSONParser();
            parser.parse(s_json);
            JSONObject json = (JSONObject) parser.parse(s_json);
            for (Object o : json.entrySet()) {
                if (o instanceof Entry) {
                    Entry entry = (Entry) o;
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if (key instanceof String && value instanceof JSONObject) {
                        String materialName = (String) key;
                        materialName = materialName.replace("minecraft:", "").toUpperCase();
                        JSONObject blockDefinition = (JSONObject) value;
                        parseMaterialInstances(materialName, blockDefinition);
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Exception parsing BlockData definitions");
            ex.printStackTrace();
        }
    }

    private static void parseMaterialInstances(String materialName, JSONObject definition) {
        if (definition.containsKey("states")) {
            Object o = definition.get("states");
            if (!(o instanceof List)) {
                System.err.println("Error: Malformed definition of " + materialName + "(Bad states)");
                return;
            }
            List states = (List) o;
            for (Object p : states) {
                if (!(p instanceof JSONObject)) {
                    System.err.println("Error: Malformed definition of " + materialName + "(Bad state)");
                    return;
                }
                JSONObject state = (JSONObject) p;

                if (!state.containsKey("id")) {
                    System.err.println("Error: Malformed definition of " + materialName + "(No id)");
                    return;
                }

                int protocolId = (int) (long) (Long) state.get("id");  // Integers are parsed as Longs. This library sucks
                Material mat = Material.valueOf(materialName);
                BlockData data;

                try {
                    if (state.containsKey("properties")) {
                        Object q = state.get("properties");
                        if (!(q instanceof JSONObject)) {
                            System.err.println("Error: Malformed definition of " + materialName + "(Bad properties)");
                            return;
                        }

                        JSONObject properties = (JSONObject) q;
                        Class<? extends BlockData> dataClass = getBlockDataClassForMaterial(mat);
                        data = dataClass.getConstructor(JSONObject.class, Material.class).newInstance(properties, mat);
                    } else {
                        data = new CraftBlockData(mat);
                    }
                    blockDataMap.put(protocolId, data);
                } catch (Exception ex) {
                    System.err.println("Error: Malformed definition of " + materialName + "(BlockData instantiation)");
                    ex.printStackTrace();
                }

            }
        }
    }

    private static Class<? extends BlockData> getBlockDataClassForMaterial(Material mat) {
        return blockDataClassMap.getOrDefault(mat.getBlockDataClass(), CraftBlockData.class);
    }

    static {
        blockDataClassMap.put(Bamboo.class, CraftBamboo.class);
        blockDataClassMap.put(Bed.class, CraftBed.class);
        blockDataClassMap.put(Bell.class, CraftBell.class);
        blockDataClassMap.put(BrewingStand.class, CraftBrewingStand.class);
        blockDataClassMap.put(BubbleColumn.class, CraftBubbleColumn.class);
        blockDataClassMap.put(Cake.class, CraftCake.class);
        blockDataClassMap.put(Campfire.class, CraftCampfire.class);
        blockDataClassMap.put(Chest.class, CraftChest.class);
        blockDataClassMap.put(Cocoa.class, CraftCocoa.class);
        blockDataClassMap.put(CommandBlock.class, CraftCommandBlock.class);
        blockDataClassMap.put(Comparator.class, CraftComparator.class);
        blockDataClassMap.put(CoralWallFan.class, CraftCoralWallFan.class);
        blockDataClassMap.put(DaylightDetector.class, CraftDaylightDetector.class);
        blockDataClassMap.put(Dispenser.class, CraftDispenser.class);
        blockDataClassMap.put(Door.class, CraftDoor.class);
        blockDataClassMap.put(EnderChest.class, CraftEnderChest.class);
        blockDataClassMap.put(EndPortalFrame.class, CraftEndPortalFrame.class);
        blockDataClassMap.put(Farmland.class, CraftFarmland.class);
        blockDataClassMap.put(Fence.class, CraftFence.class);
        blockDataClassMap.put(Fire.class, CraftFire.class);
        blockDataClassMap.put(Furnace.class, CraftFurnace.class);
        blockDataClassMap.put(Gate.class, CraftGate.class);
        blockDataClassMap.put(GlassPane.class, CraftGlassPane.class);
        blockDataClassMap.put(Hopper.class, CraftHopper.class);
        blockDataClassMap.put(Jukebox.class, CraftJukebox.class);
        blockDataClassMap.put(Ladder.class, CraftLadder.class);
        blockDataClassMap.put(Lantern.class, CraftLantern.class);
        blockDataClassMap.put(Leaves.class, CraftLeaves.class);
        blockDataClassMap.put(Lectern.class, CraftLectern.class);
        blockDataClassMap.put(NoteBlock.class, CraftNoteBlock.class);
        blockDataClassMap.put(Observer.class, CraftObserver.class);
        blockDataClassMap.put(Piston.class, CraftPiston.class);
        blockDataClassMap.put(PistonHead.class, CraftPistonHead.class);
        blockDataClassMap.put(RedstoneRail.class, CraftRedstoneRail.class);
        blockDataClassMap.put(RedstoneWallTorch.class, CraftRedstoneWallTorch.class);
        blockDataClassMap.put(RedstoneWire.class, CraftRedstoneWire.class);
        blockDataClassMap.put(Repeater.class, CraftRepeater.class);
        blockDataClassMap.put(Sapling.class, CraftSapling.class);
        blockDataClassMap.put(Scaffolding.class, CraftScaffolding.class);
        blockDataClassMap.put(SeaPickle.class, CraftSeaPickle.class);
        blockDataClassMap.put(Sign.class, CraftSign.class);
        blockDataClassMap.put(Slab.class, CraftSlab.class);
        blockDataClassMap.put(Snow.class, CraftSnow.class);
        blockDataClassMap.put(Stairs.class, CraftStairs.class);
        blockDataClassMap.put(StructureBlock.class, CraftStructureBlock.class);
        blockDataClassMap.put(Switch.class, CraftSwitch.class);
        blockDataClassMap.put(TechnicalPiston.class, CraftTechnicalPiston.class);
        blockDataClassMap.put(TNT.class, CraftTNT.class);
        blockDataClassMap.put(Trapdoor.class, CraftTrapdoor.class);
        blockDataClassMap.put(Tripwire.class, CraftTripwire.class);
        blockDataClassMap.put(TripwireHook.class, CraftTripwireHook.class);
        blockDataClassMap.put(TurtleEgg.class, CraftTurtleEgg.class);
        blockDataClassMap.put(WallSign.class, CraftWallSign.class);
        parseAllInstances();
    }
}
