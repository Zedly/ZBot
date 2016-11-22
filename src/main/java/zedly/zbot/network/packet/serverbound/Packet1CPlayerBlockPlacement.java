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
public class Packet1CPlayerBlockPlacement implements ServerBoundPacket {
    int x;
    int y;
    int z;
    byte face;
    int hand;
    double cursorX;
    double cursorY;
    double cursorZ;

    public Packet1CPlayerBlockPlacement(int x, int y, int z, byte face, int hand, double cursorX, double cursorY, double cursorZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.face = face;
        this.hand = hand;
        this.cursorX = cursorX;
        this.cursorY = cursorY;
        this.cursorZ = cursorZ;
    }

    @Override
    public int opCode() {
        return 0x1C;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException { 
       dos.writePosition(x, y, z);
        dos.writeVarInt(face);
        dos.writeVarInt(hand);
        dos.writeFloat((float) cursorX);
        dos.writeFloat((float) cursorY);
        dos.writeFloat((float) cursorZ);
    }
    
}
