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
public class Packet4BEntityEffect implements ClientBoundPacket {
    private int entityID;
    private byte effectID;
    private byte amplifier;
    private int duration;
    private boolean hideParticles;
    
    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        effectID = dis.readByte();
        amplifier = dis.readByte();
        duration = dis.readVarInt();
        hideParticles = dis.readBoolean();
    }
}
