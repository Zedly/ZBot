/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.inventory;

import zedly.zbot.api.block.Material;
import zedly.zbot.api.inventory.ItemStack;
import net.minecraft.server.NBTBase;

/**
 *
 * @author Dennis
 */
public class CraftItemStack implements ItemStack {

    protected short itemId = -1;
    protected short damageValue = 0;
    protected NBTBase nbt;
    protected byte itemCount = 0;

    public CraftItemStack() {
    }

    public CraftItemStack(int materialId, int damage) {
        itemId = (short) materialId;
        this.damageValue = (short) damage;
    }
    
    @Override
    public Material getType() {
        return Material.fromTypeId(itemId);
    }

    @Override
    public int getTypeId() {
        return itemId;
    }

    @Override
    public short getDamageValue() {
        return damageValue;
    }

    @Override
    public NBTBase getNbt() {
        return nbt;
    }

    @Override
    public byte getAmount() {
        return itemCount;
    }

    public void setItemId(short itemId) {
        this.itemId = itemId;
    }

    public void setDamageValue(short damageValue) {
        this.damageValue = damageValue;
    }

    public void setNbt(NBTBase nbt) {
        this.nbt = nbt;
    }

    public void setItemCount(byte itemCount) {
        this.itemCount = itemCount;
    }
}
