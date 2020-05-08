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
public class Packet04LoginPluginRequest implements ClientBoundPacket {

    private int messageId;
    private String channel;
    private byte[] data;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        messageId = dis.readVarInt();
        channel = dis.readString();

        if (channel.equals("minecraft:register") && packetLen == 180) {
            packetLen--;
        }
        if (channel.startsWith("multichat:")) {
            packetLen--;
        }
        
        
        
        data = new byte[packetLen - 1 - channel.length()];
        dis.readFully(data);
    }
}
//Refactored ancestor. Review data strcuture