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
public class Packet0FPlayer implements ServerBoundPacket {
    boolean onGround;

    public Packet0FPlayer(boolean onGround) {
        this.onGround = onGround;
    }

    @Override
    public int opCode() {
        return 0x0F;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeBoolean(onGround);
    }
    
}
