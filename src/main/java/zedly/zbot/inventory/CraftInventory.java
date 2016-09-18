/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.inventory;

import zedly.zbot.GameContext;
import zedly.zbot.inventory.ItemStack;
import zedly.zbot.inventory.Inventory;
import zedly.zbot.network.packet.serverbound.Packet07ClickWindow;

/**
 *
 * @author Dennis
 */
public class CraftInventory implements Inventory {

    protected ItemStack itemOnCursor;
    protected final ItemStack[] items = new ItemStack[46];
    protected int selectedSlot = 36;
    private final CraftItemStack nullItem = new CraftItemStack();
    private final GameContext context;
    private int activeWindowId = 0;
    private int transactionId = 0;

    public CraftInventory(GameContext context) {
        this.context = context;
    }

    public synchronized ItemStack getSlot(int i) {
        if(i == -1) {
            return itemOnCursor;
        }
        return items[i];
    }

    public synchronized int getSelectedSlot() {
        return selectedSlot;
    }

    public synchronized ItemStack getItemInHand() {
        return items[selectedSlot];
    }

    public synchronized ItemStack getItemOnCursor() {
        return itemOnCursor;
    }
    
    public synchronized void clickSlot(int slot, int mode, int button) {
        if (slot == -999) {
            context.getUpThread().sendPacket(new Packet07ClickWindow((byte) activeWindowId, (short) slot, (byte) button, (short) transactionId++, (byte) mode, nullItem));
        } else {
            ItemStack is = items[slot];
            items[slot] = itemOnCursor;
            itemOnCursor = is;
            context.getUpThread().sendPacket(new Packet07ClickWindow((byte) activeWindowId, (short) slot, (byte) button, (short) transactionId++, (byte) mode, is));
        }
    }

    public synchronized void setSlot(int i, ItemStack is) {
        if (i == -1) {
            itemOnCursor = is;
        } else {
            items[i] = is;
        }
    }

    public synchronized void selectSlot(int i) {
        selectedSlot = 35 + i;
    }

    public synchronized void reset() {
        for (int i = 0; i < items.length; i++) {
            items[i] = null;
        }
    }

}
