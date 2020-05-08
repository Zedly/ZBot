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
public class Packet0BPluginMessage implements ServerBoundPacket {
    String channel;
    byte[] data;

    public Packet0BPluginMessage(String channel, byte[] data) {
        this.channel = channel;
        this.data = data;
    }

    @Override
    public int opCode() {
        return 0x0B;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeString(channel);
        dos.write(data);
    }
    
}
//Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture