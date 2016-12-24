/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.inventory;

import org.bukkit.Material;
import net.minecraft.server.NBTBase;

/**
 *
 * @author Dennis
 */
public class CraftItemStack implements ItemStack {

    protected short itemId = -1;
    protected short data = 0;
    protected NBTBase nbt;
    protected byte itemCount = 0;

    public CraftItemStack() {
    }

    public CraftItemStack(int materialId, int damage) {
        itemId = (short) materialId;
        this.data = (short) damage;
    }
    
    @Override
    public synchronized Material getType() {
        return Material.getMaterial(itemId);
    }

    @Override
    public synchronized int getTypeId() {
        return itemId;
    }
    
    @Override
    public synchronized short getData() {
        return data;
    }

    @Override
    public synchronized NBTBase getNbt() {
        return nbt;
    }

    @Override
    public synchronized byte getAmount() {
        return itemCount;
    }

    public synchronized void setItemId(short itemId) {
        this.itemId = itemId;
    }

    public synchronized void setData(short damageValue) {
        this.data = damageValue;
    }

    public synchronized void setNbt(NBTBase nbt) {
        this.nbt = nbt;
    }

    public synchronized void setItemCount(byte itemCount) {
        this.itemCount = itemCount;
    }
}
