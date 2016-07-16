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
public class Packet15SteerVehicle implements ServerBoundPacket {

    float sideways;
    float forward;
    byte flags;
    boolean jump;
    boolean unmount;

    public Packet15SteerVehicle(float sideways, float forward, boolean jump, boolean unmount) {
        this.sideways = sideways;
        this.forward = forward;
        if (jump) {
            flags = 0x1;
        }
        if (unmount) {
            flags |= 0x2;
        }
    }

    @Override
    public int opCode() {
        return 0x15;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeFloat(sideways);
        dos.writeFloat(forward);
        dos.writeByte(flags);
    }

}
