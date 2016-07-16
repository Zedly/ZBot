/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import zedly.zbot.network.ExtendedDataOutputStream;

import java.io.IOException;

/**
 * @author Dennis
 */
public class Packet03ClientStatus implements ServerBoundPacket {

    int actionId;

    public Packet03ClientStatus(int actionId) {
        this.actionId = actionId;
    }

    @Override
    public int opCode() {
        return 0x03;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(actionId);
    }
}
