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
import zedly.zbot.environment.BlockData;
import zedly.zbot.environment.CraftBlockData;

/**
 *
 * @author Dennis
 */
public class BlockDataIds {

    private static final HashMap<Integer, BlockData> blockDataMap = new HashMap<>();

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
                        data = dataClass.getConstructor(Material.class, JSONObject.class).newInstance(mat, properties);
                    } else {
                        data = new CraftBlockData(mat);
                    }
                    blockDataMap.put(protocolId, data);
                } catch (Exception ex) {
                    System.err.println("Error: Malformed definition of " + materialName + "(BlockData instantiation)");
                }

            }
        }
    }

    private static Class<? extends BlockData> getBlockDataClassForMaterial(Material mat) {
        return CraftBlockData.class;
    }

    static {
        parseAllInstances();
    }
}
