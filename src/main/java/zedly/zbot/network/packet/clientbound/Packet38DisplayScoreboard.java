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
public class Packet38DisplayScoreboard implements ClientBoundPacket {
    private byte position;
    private String scoreName;
    
    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        position = dis.readByte();
        scoreName = dis.readString();
    }

    public byte getPosition() {
        return position;
    }

    public String getScoreName() {
        return scoreName;
    }
    
}
