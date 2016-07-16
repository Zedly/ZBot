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
public class Packet0BKeepAlive implements ServerBoundPacket {
    int keepAliveId;

    public Packet0BKeepAlive(int keepAliveId) {
        this.keepAliveId = keepAliveId;
    }

    @Override
    public int opCode() {
        return 0x0B;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(keepAliveId);
    }
    
}
