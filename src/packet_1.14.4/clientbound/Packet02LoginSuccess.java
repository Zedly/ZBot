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
public class Packet02LoginSuccess implements ClientBoundPacket {
    
    private String uuid;
    private String username;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        uuid = dis.readString();
        username = dis.readString();
    }

    public String getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }
    
}
