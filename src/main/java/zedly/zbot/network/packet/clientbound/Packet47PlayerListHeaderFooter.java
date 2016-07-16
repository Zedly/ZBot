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
public class Packet47PlayerListHeaderFooter implements ClientBoundPacket {
    private String header;
    private String footer;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        header = dis.readString();
        footer = dis.readString();
    }
    
}
