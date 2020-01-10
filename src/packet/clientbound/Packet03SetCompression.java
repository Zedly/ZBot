/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.packet.clientbound.ClientBoundPacket;

/**
 *
 * @author Dennis
 */
public class Packet03SetCompression implements ClientBoundPacket {
    
    private int threshold;

    public int opCode() {
        return 0x03;
    }

    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        threshold = dis.readVarInt();
    }

    public int getThreshold() {
        return threshold;
    }
    
}
