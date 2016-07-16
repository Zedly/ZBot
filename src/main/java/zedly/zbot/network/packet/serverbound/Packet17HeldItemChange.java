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
public class Packet17HeldItemChange implements ServerBoundPacket {
    int slot;

    public Packet17HeldItemChange(int slot) {
        this.slot = slot;
    }

    @Override
    public int opCode() {
        return 0x17;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeShort(slot);
    }
    
}
