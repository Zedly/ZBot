/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.inventory.ItemStack;
import zedly.zbot.network.ExtendedDataOutputStream;

/**
 *
 * @author Dennis
 */
public class Packet07ClickWindow implements ServerBoundPacket {
    byte windowId;
    short slot;
    byte button;
    short actionId;
    byte mode;
    ItemStack clickedItem;

    public Packet07ClickWindow(byte windowId, short slot, byte button, short actionId, byte mode, ItemStack clickedItem) {
        this.windowId = windowId;
        this.slot = slot;
        this.button = button;
        this.actionId = actionId;
        this.mode = mode;
        this.clickedItem = clickedItem;
    }

    @Override
    public int opCode() {
        return 0x07;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(windowId);
        dos.writeShort(slot);
        dos.writeByte(button);
        dos.writeShort(actionId);
        dos.writeVarInt(mode);
        dos.writeSlot(clickedItem);
    }
    
}
