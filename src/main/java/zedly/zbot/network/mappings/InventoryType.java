/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.mappings;

import java.util.HashMap;

/**
 *
 * @author Dennis
 */
public enum InventoryType {

    MINECRAFT_GENERIC_9X1,
    MINECRAFT_GENERIC_9X2,
    MINECRAFT_GENERIC_9X3,
    MINECRAFT_GENERIC_9X4,
    MINECRAFT_GENERIC_9X5,
    MINECRAFT_GENERIC_9X6,
    MINECRAFT_GENERIC_3X3,
    MINECRAFT_ANVIL,
    MINECRAFT_BEACON,
    MINECRAFT_BLAST_FURNACE,
    MINECRAFT_BREWING_STAND,
    MINECRAFT_CRAFTING,
    MINECRAFT_ENCHANTMENT,
    MINECRAFT_FURNACE,
    MINECRAFT_GRINDSTONE,
    MINECRAFT_HOPPER,
    MINECRAFT_LECTERN,
    MINECRAFT_LOOM,
    MINECRAFT_MERCHANT,
    MINECRAFT_SHULKER_BOX,
    MINECRAFT_SMOKER,
    MINECRAFT_CARTOGRAPHY,
    MINECRAFT_STONECUTTER;

    private static final HashMap<Integer, InventoryType> FOR_IDS = new HashMap<>();

    public static InventoryType fromProtocolId(int protocolId) {
        return FOR_IDS.get(protocolId);
    }

    static {
        FOR_IDS.put(0, MINECRAFT_GENERIC_9X1);
        FOR_IDS.put(1, MINECRAFT_GENERIC_9X2);
        FOR_IDS.put(2, MINECRAFT_GENERIC_9X3);
        FOR_IDS.put(3, MINECRAFT_GENERIC_9X4);
        FOR_IDS.put(4, MINECRAFT_GENERIC_9X5);
        FOR_IDS.put(5, MINECRAFT_GENERIC_9X6);
        FOR_IDS.put(6, MINECRAFT_GENERIC_3X3);
        FOR_IDS.put(7, MINECRAFT_ANVIL);
        FOR_IDS.put(8, MINECRAFT_BEACON);
        FOR_IDS.put(9, MINECRAFT_BLAST_FURNACE);
        FOR_IDS.put(10, MINECRAFT_BREWING_STAND);
        FOR_IDS.put(11, MINECRAFT_CRAFTING);
        FOR_IDS.put(12, MINECRAFT_ENCHANTMENT);
        FOR_IDS.put(13, MINECRAFT_FURNACE);
        FOR_IDS.put(14, MINECRAFT_GRINDSTONE);
        FOR_IDS.put(15, MINECRAFT_HOPPER);
        FOR_IDS.put(16, MINECRAFT_LECTERN);
        FOR_IDS.put(17, MINECRAFT_LOOM);
        FOR_IDS.put(18, MINECRAFT_MERCHANT);
        FOR_IDS.put(19, MINECRAFT_SHULKER_BOX);
        FOR_IDS.put(20, MINECRAFT_SMOKER);
        FOR_IDS.put(21, MINECRAFT_CARTOGRAPHY);
        FOR_IDS.put(22, MINECRAFT_STONECUTTER);
    }

}
