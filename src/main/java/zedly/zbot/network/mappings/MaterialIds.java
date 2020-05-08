/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.mappings;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import zedly.zbot.Material;

/**
 *
 * @author Dennis
 */
public class MaterialIds {

    private static final BiMap<Integer, zedly.zbot.Material> itemIdMap = HashBiMap.create();
    private static final BiMap<Integer, zedly.zbot.Material> blockIdMap = HashBiMap.create();
    private static final BiMap<zedly.zbot.Material, Integer> itemIdMapInverse;
    private static final BiMap<zedly.zbot.Material, Integer> blockIdMapInverse;

    public static Material fromItemId(int itemId) {
        return itemIdMap.get(itemId);
    }

    public static Material fromBlockId(int blockId) {
        return itemIdMap.get(blockId);
    }

    public static int getItemId(Material mat) {
        return itemIdMapInverse.get(mat);
    }

    public static int getBlockId(Material mat) {
        return blockIdMapInverse.get(mat);
    }

    private static final void parseItemIds() {
        try {
            RegistryProtocolIdMapper.mapFlatRegistry("minecraft:item", (namespacedKey, protocolId) -> {
                try {
                    String enumValueString = namespacedKey.replace("minecraft:", "").toUpperCase();
                    Material mat = Material.valueOf(enumValueString);
                    itemIdMap.put(protocolId, mat);
                } catch (IllegalArgumentException ex) {
                    System.err.println("No Material value for " + namespacedKey);
                }
            });
        } catch (IOException | ParseException ex) {
        }
    }

    private static final void parseBlockEntityIds() {
        try {
            RegistryProtocolIdMapper.mapFlatRegistry("minecraft:block", (namespacedKey, protocolId) -> {
                try {
                    String enumValueString = namespacedKey.replace("minecraft:", "").toUpperCase();
                    Material mat = Material.valueOf(enumValueString);
                    blockIdMap.put(protocolId, mat);
                } catch (IllegalArgumentException ex) {
                    System.err.println("No Material value for " + namespacedKey);
                }
            });
        } catch (IOException | ParseException ex) {
        }
    }

    static {
        parseItemIds();
        parseBlockEntityIds();
        itemIdMapInverse = itemIdMap.inverse();
        blockIdMapInverse = blockIdMap.inverse();
    }
}
