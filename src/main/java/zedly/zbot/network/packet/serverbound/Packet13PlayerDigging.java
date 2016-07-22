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
public class Packet13PlayerDigging implements ServerBoundPacket {
    byte status;
    int x;
    int y;
    int z;
    byte face;

    public Packet13PlayerDigging(int status, int x, int y, int z, int face) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.face = (byte) face;
    }

    @Override
    public int opCode() {
        return 0x13;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(status);
        dos.writePosition(x, y, z);
        dos.writeByte(face);
    }
    
}
