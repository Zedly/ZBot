/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.packet.clientbound.ClientBoundPacket;

//Login, Client

public class Packet00Play implements ClientBoundPacket {

    private String JSONData;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        JSONData = dis.readString();
    }

    public String getJSONData() {
        return JSONData;
    }
    
}
Refactored ancestor. Review data strcuture