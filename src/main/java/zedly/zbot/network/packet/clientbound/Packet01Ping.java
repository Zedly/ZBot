/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.clientbound.ClientBoundPacket;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */
public class Packet01Ping implements ServerBoundPacket, ClientBoundPacket {
    
    long time;

    public Packet01Ping() {
    }

    public Packet01Ping(long time) {
        this.time = time;
    }

    @Override
    public int opCode() {
        return 0x01;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeLong(time);
    }

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        time = dis.readLong();
    }

    public long getTime() {
        return time;
    }
    
}
