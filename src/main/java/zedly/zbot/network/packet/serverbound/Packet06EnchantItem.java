/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */
public class Packet06EnchantItem implements ServerBoundPacket {
    byte windowId;
    byte enchantment;

    public Packet06EnchantItem(byte windowId, byte enchantment) {
        this.windowId = windowId;
        this.enchantment = enchantment;
    }

    @Override
    public int opCode() {
        return 0x06;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(windowId);
        dos.writeByte(enchantment);
    }
    
}
