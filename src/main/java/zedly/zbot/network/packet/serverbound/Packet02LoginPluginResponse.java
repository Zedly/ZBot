/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */
public class Packet02LoginPluginResponse implements ServerBoundPacket {

    int messageId;
    boolean successful;
    byte[] data;

    public Packet02LoginPluginResponse(int messageId, boolean succ, byte[] data) {
        this.messageId = messageId;
        this.successful = succ;
        this.data = data;
    }

    @Override
    public int opCode() {
        return 0x02;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(messageId);
        dos.writeBoolean(successful);
        if (successful) {
            dos.write(data);
        }
    }
}