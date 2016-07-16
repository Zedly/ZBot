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
public class Packet11SteerBoat implements ServerBoundPacket {
    private boolean b1;
    private boolean b2;

    public Packet11SteerBoat(boolean b1, boolean b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    @Override
    public int opCode() {
        return 0x11;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeBoolean(b1);
        dos.writeBoolean(b2);
    }
    
}
