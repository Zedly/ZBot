/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet37HeldItemChange implements ClientBoundPacket {
    byte slot;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        slot = dis.readByte();
    }

    public byte getSlot() {
        return slot;
    }
    
}
