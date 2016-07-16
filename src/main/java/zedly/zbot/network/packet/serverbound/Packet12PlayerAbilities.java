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
public class Packet12PlayerAbilities implements ServerBoundPacket {
    byte flags;
    float flySpeed;
    float walkSpeed;

    public Packet12PlayerAbilities(byte flags, float flySpeed, float walkSpeed) {
        this.flags = flags;
        this.flySpeed = flySpeed;
        this.walkSpeed = walkSpeed;
    }

    @Override
    public int opCode() {
        return 0x12;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(flags);
        dos.writeFloat(flySpeed);
        dos.writeFloat(walkSpeed);
    }
    
}
