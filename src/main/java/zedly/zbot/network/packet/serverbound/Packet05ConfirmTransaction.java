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
public class Packet05ConfirmTransaction implements ServerBoundPacket {
    byte windowId;
    short actionId;
    boolean accepted;

    public Packet05ConfirmTransaction(byte windowId, short actionId, boolean accepted) {
        this.windowId = windowId;
        this.actionId = actionId;
        this.accepted = accepted;
    }

    @Override
    public int opCode() {
        return 0x05;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(windowId);
        dos.writeShort(actionId);
        dos.writeBoolean(accepted);
    }
    
}
