/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

//Login, Server

public class Packet00LoginStart implements ServerBoundPacket {

    String name;

    public Packet00LoginStart() {
    }

    public Packet00LoginStart(String name) {
        this.name = name;
    }

    @Override
    public int opCode() {
        return 0x00;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeString(name);
    }
    
}
