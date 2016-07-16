/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
//Play, Serverbound

public class Packet00TeleportConfirm implements ServerBoundPacket {
    int teleportId;

    public Packet00TeleportConfirm(int teleportId) {
        this.teleportId = teleportId;
    }

    @Override
    public int opCode() {
        return 0x00;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(teleportId);
    }
    
}
