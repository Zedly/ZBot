/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.api.event.SlotUpdateEvent;
import zedly.zbot.api.inventory.ItemStack;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * @author Dennis
 */
public class Packet16SetSlot implements ClientBoundPacket {

    private byte windowID;
    private short slotId;
    private ItemStack slotData;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readByte();
        slotId = dis.readShort();
        slotData = dis.readSlot();
    }

    @Override
    public void process(GameContext context) {
        context.getSelf().getInventory().setSlot(slotId, slotData);
        context.getMainThread().fireEvent(new SlotUpdateEvent(slotId, slotData));
    }
}
