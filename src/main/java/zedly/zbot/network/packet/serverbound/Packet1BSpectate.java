/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import java.util.UUID;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */
public class Packet1BSpectate implements ServerBoundPacket {
    UUID uuid;

    public Packet1BSpectate(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public int opCode() {
        return 0x1B;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeUUID(uuid);
    }
    
}
