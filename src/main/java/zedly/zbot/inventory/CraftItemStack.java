/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import zedly.zbot.network.mappings.MaterialIds;
import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.NBTTagString;
import zedly.zbot.Material;

/**
 *
 * @author Dennis
 */
public class CraftItemStack implements ItemStack {

    protected int typeId;
    protected NBTBase nbt;
    protected int itemCount = 0;

    public CraftItemStack() {
    }

    public CraftItemStack(int materialId) {
        this.typeId = materialId;
    }
    
    public CraftItemStack(Material mat) {
        this(mat, 1);
    }
    
    public CraftItemStack(Material mat, int amount) {
        this.typeId = MaterialIds.getItemId(mat);
        this.itemCount = 1;
    }

    @Override
    public synchronized Material getType() {
        return MaterialIds.fromItemId(typeId);
    }
    
    public synchronized int getTypeId() {
        return typeId;
    }

    @Override
    public synchronized NBTBase getNbt() {
        return nbt;
    }

    @Override
    public Map<Enchantment, Integer> getEnchantments() {
        Map<Enchantment, Integer> enchList = new HashMap<>();

        if (nbt.getId() != 10) {
            System.err.println("ItemStack has malformed NBT data: Root tag type " + nbt.getId());
            return null;
        }

        NBTTagCompound rootCompoundTag = (NBTTagCompound) nbt;

        NBTBase enchTag;

        if (getType() == Material.ENCHANTED_BOOK) {
            if (!rootCompoundTag.hasKey("StoredEnchantments")) {
                return null;
            }
            enchTag = rootCompoundTag.getTag("StoredEnchantments");
            if (enchTag.getId() != 9) {
                System.err.println("ItemStack has malformed NBT data: ench tag type " + enchTag.getId());
                return null;
            }
        } else {
            if (!rootCompoundTag.hasKey("ench")) {
                return null;
            }
            enchTag = rootCompoundTag.getTag("ench");
            if (enchTag.getId() != 9) {
                System.err.println("ItemStack has malformed NBT data: ench tag type " + enchTag.getId());
                return null;
            }
        }

        NBTTagList enchListTag = (NBTTagList) enchTag;
        int count = enchListTag.tagCount();
        for (int i = 0; i < count; i++) {
            NBTBase enchantmentTag = enchListTag.tagAt(i);
            if (enchantmentTag.getId() != 10) {
                System.err.println("ItemStack has malformed NBT data: enchantment tag " + i + " type " + enchantmentTag.getId());
                continue;
            }
            NBTTagCompound enchantmentCompoundTag = (NBTTagCompound) enchantmentTag;
            if (!enchantmentCompoundTag.hasKey("id") || !enchantmentCompoundTag.hasKey("lvl")) {
                System.err.println("ItemStack has malformed NBT data: ench tag " + i + " not id+lvl");
                continue;
            }

            Enchantment ench = Enchantment.byCanonicalId(enchantmentCompoundTag.getString("id"));
            int lvl = enchantmentCompoundTag.getShort("lvl");

            if (ench == null) {
                System.err.println("ItemStack has unknown enchantment, id " + enchantmentCompoundTag.getString("id"));
                continue;
            }
            enchList.put(ench, lvl);
        }
        return enchList;
    }

    @Override
    public List<String> getLore() {
        List<String> loreList = new ArrayList<>();

        if (nbt.getId() != 10) {
            System.err.println("ItemStack has malformed NBT data: Root tag type " + nbt.getId());
            return null;
        }

        NBTTagCompound rootTag = (NBTTagCompound) nbt;

        if (!rootTag.hasKey("display")) {
            return null;
        }

        NBTBase displayTag = rootTag.getTag("display");
        if (displayTag.getId() != 10) {
            System.err.println("ItemStack has malformed NBT data: display tag type " + displayTag.getId());
            return null;
        }

        NBTTagCompound displayCompoundTag = (NBTTagCompound) displayTag;
        if (!displayCompoundTag.hasKey("Lore")) {
            return null;
        }

        NBTBase loreTag = displayCompoundTag.getTag("Lore");
        if (loreTag.getId() != 9) {
            System.err.println("ItemStack has malformed NBT data: Lore tag type " + loreTag.getId());
            return null;
        }

        NBTTagList loreListTag = (NBTTagList) loreTag;
        int count = loreListTag.tagCount();
        for (int i = 0; i < count; i++) {
            NBTBase lineTag = loreListTag.tagAt(i);
            if (lineTag.getId() != 8) {
                System.err.println("ItemStack has malformed NBT data: LoreLine tag " + i + " type " + lineTag.getId());
                continue;
            }
            NBTTagString lineStringTag = (NBTTagString) lineTag;
            loreList.add(zedly.zbot.Util.interpretJson(lineStringTag.data));
        }
        return loreList;
    }

    @Override
    public String getDisplayName() {
        if (nbt.getId() != 10) {
            System.err.println("ItemStack has malformed NBT data: Root tag type " + nbt.getId());
            return null;
        }

        NBTTagCompound rootTag = (NBTTagCompound) nbt;

        if (!rootTag.hasKey("display")) {
            return null;
        }

        NBTBase displayTag = rootTag.getTag("display");
        if (displayTag.getId() != 10) {
            System.err.println("ItemStack has malformed NBT data: display tag type " + displayTag.getId());
            return null;
        }

        NBTTagCompound displayCompoundTag = (NBTTagCompound) displayTag;
        if (!displayCompoundTag.hasKey("Name")) {
            return null;
        }

        NBTBase nameTag = displayCompoundTag.getTag("Name");
        if (nameTag.getId() != 8) {
            System.err.println("ItemStack has malformed NBT data: Name tag type " + nameTag.getId());
            return null;
        }

        NBTTagString nameStringTag = (NBTTagString) nameTag;
        return nameStringTag.data;
    }

    @Override
    public boolean hasEnchantments() {
        return getEnchantments() != null;
    }

    @Override
    public boolean hasLore() {
        return getLore() != null;
    }

    @Override
    public boolean hasDisplayName() {
        return getDisplayName() != null;
    }

    @Override
    public synchronized int getAmount() {
        return itemCount;
    }

    public synchronized void setItemId(int itemId) {
        this.typeId = itemId;
    }

    public synchronized void setNbt(NBTBase nbt) {
        this.nbt = nbt;
    }

    public synchronized void setItemCount(byte itemCount) {
        this.itemCount = itemCount;
    }
}
