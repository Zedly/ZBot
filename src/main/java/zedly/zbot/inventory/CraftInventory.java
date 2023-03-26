/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.inventory;

import java.util.LinkedList;
import org.apache.commons.lang3.tuple.Pair;
import zedly.zbot.GameContext;
import zedly.zbot.network.packet.serverbound.Packet08ClickWindow;

/**
 *
 * @author Dennis
 */
public abstract class CraftInventory implements Inventory {

    protected final GameContext context;
    protected final ItemStack[] items;
    protected final int windowId;
    protected final int staticBlockOffset;
    protected ItemStack itemOnCursor;
    protected int selectedSlot = 0;
    protected int transactionId = 0;
    protected boolean open = true;
    protected boolean initialized = false;
    protected boolean changed = false;

    public CraftInventory(GameContext context, int size, int staticInventoryOffset, int windowId) {
        this.context = context;
        items = new ItemStack[size];
        this.windowId = windowId;
        this.staticBlockOffset = staticInventoryOffset;
    }

    @Override
    public int size() {
        return items.length;
    }

    public int windowId() {
        return windowId;
    }

    @Override
    public int getStaticOffset() {
        return staticBlockOffset;
    }

    @Override
    public ItemStack getSlot(int i) {
        return items[i];
    }

    public void dropStackOnCursor() {
        click(-999, 0, 1);
    }

    public void dropItemOnCursor() {
        click(-999, 0, 1);
    }

    @Override
    public void click(int slot, int mode, int button) {
        if (slot == -999) {
            context.getUpThread().sendPacket(new Packet08ClickWindow((byte) windowId, 0, (short) slot, (byte) button, (short) transactionId++, (byte) mode, new LinkedList<Pair<Integer, ItemStack>>(), null));
        } else {
            ItemStack is = items[slot];
            items[slot] = itemOnCursor;
            itemOnCursor = is;
            context.getUpThread().sendPacket(new Packet08ClickWindow((byte) windowId, 0, (short) slot, (byte) button, (short) transactionId++, (byte) mode, new LinkedList<Pair<Integer, ItemStack>>(), is));
        }
        changed = true;
    }

    public void selectSlot(int i) {
        selectedSlot = i;
    }

    public void setSlot(int slot, ItemStack is) {
        if (slot >= 0 && slot < items.length) {
            items[slot] = is;
        } else if (slot == -1) {
            itemOnCursor = is;
        } else {
            System.err.println("Invalid slot ID " + slot + " in inventory " + getClass() + " set to " + is);
        }
        initialized = true;
    }

    public void reset() {
        for (int i = 0; i < items.length; i++) {
            items[i] = null;
        }
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public int getSelectedSlot() {
        return selectedSlot + staticBlockOffset + 27;
    }

    @Override
    public ItemStack getItemInHand() {
        return items[selectedSlot + staticBlockOffset + 27];
    }

    @Override
    public ItemStack getItemOnCursor() {
        return itemOnCursor;
    }

    public void close() {
        open = false;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public abstract void setProperty(int property, int value);

    public void rollbackTransactionId() {
        transactionId--;
    }

    public boolean changed() {
        return changed;
    }

}
