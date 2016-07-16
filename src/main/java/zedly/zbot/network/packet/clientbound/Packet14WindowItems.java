/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.api.event.WindowItemsEvent;
import zedly.zbot.api.inventory.ItemStack;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * @author Dennis
 */
public class Packet14WindowItems implements ClientBoundPacket {

    private int windowID;
    private ItemStack[] slotData;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readUnsignedByte();
        short count = dis.readShort();
        slotData = new ItemStack[count];
        for (int i = 0; i < count; i++) {
            slotData[i] = dis.readSlot();
        }
    }

    @Override
    public void process(GameContext context) {
        if (windowID == 0) {
            for (int i = 0; i < slotData.length; i++) {
                context.getSelf().getInventory().setSlot(i, slotData[i]);
            }
        }
        context.getMainThread().fireEvent(new WindowItemsEvent(windowID, slotData));
    }

}
