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
public class Packet34EntityHeadLook implements ClientBoundPacket {
    private int entityID;
    private byte headYaw;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        headYaw = dis.readByte();
    }

    public int getEntityID() {
        return entityID;
    }

    public byte getHeadYaw() {
        return headYaw;
    }
    
}
