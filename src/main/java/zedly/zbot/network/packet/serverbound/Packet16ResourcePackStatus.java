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
public class Packet16ResourcePackStatus implements ServerBoundPacket {
    String channel;
    int status;

    public Packet16ResourcePackStatus(String channel, int status) {
        this.channel = channel;
        this.status = status;
    }

    @Override
    public int opCode() {
        return 0x16;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(status);
    }
    
}
