/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import zedly.zbot.network.*;
import java.io.IOException;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

//Handshaking

public class Packet00Handshake implements ServerBoundPacket {

    int protocolVersion;
    String serverIP;
    int serverPort;
    int state;

    public Packet00Handshake(int protocolVersion, String serverIP, int serverPort, int state) {
        this.protocolVersion = protocolVersion;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.state = state;
    }

    @Override
    public int opCode() {
        return 0x00;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(protocolVersion);
        dos.writeString(serverIP);
        dos.writeShort(serverPort);
        dos.writeVarInt(state);
    }
    
}
