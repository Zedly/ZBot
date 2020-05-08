/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.packet.clientbound.ClientBoundPacket;

// Status, Clientbound

public class Packet00Response implements ClientBoundPacket {

    private String JSONResponse;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        JSONResponse = dis.readString();
    }

    public String getJSONResponse() {
        return JSONResponse;
    }
    
}
