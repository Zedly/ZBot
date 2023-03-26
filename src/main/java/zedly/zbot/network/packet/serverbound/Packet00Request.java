/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

// Status, Serverbound

public class Packet00Request implements ServerBoundPacket {

    @Override
    public int opCode() {
        return 0x00;
    }

    public Packet00Request() {
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        //This packet has no payload
    }
    
}
