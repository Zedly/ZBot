/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet2BPlayerAbilities implements ClientBoundPacket {
    private byte flags;
    private float flySpeed;
    private float walkSpeed;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        flags = dis.readByte();
        flySpeed = dis.readFloat();
        walkSpeed = dis.readFloat();
    }

    public byte getFlags() {
        return flags;
    }

    public float getFlySpeed() {
        return flySpeed;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }
    
}
