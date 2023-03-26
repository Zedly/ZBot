/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.inventory;

import zedly.zbot.GameContext;
import zedly.zbot.network.packet.serverbound.Packet07ClickWindowButton;

/**
 *
 * @author Dennis
 */
public class CraftEnchantingTableInventory extends CraftExternalInventory implements EnchantingTableInventory {

    private final int[] levelCosts = {0, 0, 0};
    private final Enchantment[] enchantments = {null, null, null};
    private final int[] levels = {-1, -1, -1};

    public CraftEnchantingTableInventory(GameContext context, int windowId, String title) {
        super(context, 38, 2, windowId, title);
    }

    @Override
    public void setProperty(int property, int value) {
        switch (property) {
            case 0:
                levelCosts[0] = value;
                break;
            case 1:
                levelCosts[1] = value;
                break;
            case 2:
                levelCosts[2] = value;
                break;
            case 4:
                enchantments[0] = (value == -1) ? null : Enchantment.byId(value);
                break;
            case 5:
                enchantments[1] = (value == -1) ? null : Enchantment.byId(value);
                break;
            case 6:
                enchantments[2] = (value == -1) ? null : Enchantment.byId(value);
                break;
            case 7:
                levels[0] = value;
            case 8:
                levels[1] = value;
            case 9:
                levels[2] = value;
        }
    }

    @Override
    public EnchantOption[] getEnchantOptions() {
        EnchantOption[] options = null;
        if (enchantments[2] != null && levels[2] > 0) {
            options = new EnchantOption[3];
            options[2] = new CraftEnchantOption(levelCosts[2], enchantments[2], levels[2]);
        } else if (enchantments[0] != null && levels[0] > 0) {
            if (options == null) {
                options = new EnchantOption[2];
            }
            options[1] = new CraftEnchantOption(levelCosts[1], enchantments[1], levels[1]);
        } else if (enchantments[0] != null && levels[0] > 0) {
            if (options == null) {
                options = new EnchantOption[2];
            }
            options[1] = new CraftEnchantOption(levelCosts[0], enchantments[0], levels[0]);
        } else {
            options = new EnchantOption[0];
        }
        return options;
    }

    @Override
    public EnchantOption getEnchantOption(int slot) {
        if (slot >= 0 && slot < 3) {
            if (enchantments[slot] != null && levels[slot] > 0) {
                return new CraftEnchantOption(levelCosts[slot], enchantments[slot], levels[slot]);
            }
        }
        return null;
    }

    @Override
    public void enchant(int slot) {
        if (slot >= 0 && slot < 3) {
            context.getUpThread().sendPacket(new Packet07ClickWindowButton(windowId, slot));
        }
    }
}
