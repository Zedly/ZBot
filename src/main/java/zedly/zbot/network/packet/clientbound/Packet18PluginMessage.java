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
public class Packet18PluginMessage implements ClientBoundPacket {
    private String channel;
    private byte[] data;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        if(packetLen == 161)
            packetLen = 160;
        
        channel = dis.readString();
        data = new byte[packetLen - 1 - channel.length()];
        dis.readFully(data);
    }
}