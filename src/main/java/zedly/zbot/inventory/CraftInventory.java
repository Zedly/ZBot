/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.inventory;

import zedly.zbot.GameContext;
import zedly.zbot.network.packet.serverbound.Packet07ClickWindow;

/**
 *
 * @author Dennis
 */
public abstract class CraftInventory implements Inventory {

    private final GameContext context;
    private final int windowId;
    private final ItemStack[] items;
    private ItemStack itemOnCursor;
    protected int selectedSlot = 36;
    private int transactionId = 0;
    private boolean open = true;

    protected CraftInventory(GameContext context, int size, int windowId) {
        this.context = context;
        items = new ItemStack[size];
        this.windowId = windowId;
    }

    @Override
    public ItemStack getSlot(int slot) {
        if (slot < 0 || slot > items.length) {
            return null;
        }
        return items[slot];
    }

    @Override
    public int internalSlot(int slot) {
        return items.length - 36 + slot;
    }

    @Override
    public int hotbarSlot(int slot) {
        return items.length - 9 + slot;
    }

    
    public void setSlot(int slot, ItemStack is) {
        if (slot == -1) {
            itemOnCursor = is;
        } else if (slot >= 0 && slot < items.length) {
            items[slot] = is;
        }
    }

    @Override
    public int getSelectedSlot() {
        return selectedSlot;
    }

    @Override
    public ItemStack getItemInHand() {
        return items[selectedSlot];
    }

    @Override
    public ItemStack getItemOnCursor() {
        return itemOnCursor;
    }

    public void dropStackOnCursor() {
        click(-999, 0, 1);
    }

    public void dropItemOnCursor() {
        click(-999, 0, 1);
    }

    public void swapItems(int slot1, int slot2) {

    }

    @Override
    public void click(int slot, int mode, int button) {
        if (slot == -999) {
            context.getUpThread().sendPacket(new Packet07ClickWindow((byte) windowId, (short) slot, (byte) button, (short) transactionId++, (byte) mode, null));
        } else {
            ItemStack is = items[slot];
            items[slot] = itemOnCursor;
            itemOnCursor = is;
            context.getUpThread().sendPacket(new Packet07ClickWindow((byte) windowId, (short) slot, (byte) button, (short) transactionId++, (byte) mode, is));
        }
    }

    public void selectSlot(int i) {
        selectedSlot = 36 + i;
    }

    public boolean isOpen() {
        return open;
    }

    public void close() {
        open = false;
    }

    public void reset() {
        for (int i = 0; i < items.length; i++) {
            items[i] = null;
        }
    }
}
