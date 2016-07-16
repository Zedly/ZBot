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
public class Packet14EntityAction implements ServerBoundPacket {
    int entityId;
    int actionId;
    int jumpBoost;

    public Packet14EntityAction(int entityId, int actionId, int jumpBoost) {
        this.entityId = entityId;
        this.actionId = actionId;
        this.jumpBoost = jumpBoost;
    }

    @Override
    public int opCode() {
        return 0x14;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(entityId);
        dos.writeVarInt(actionId);
        dos.writeVarInt(jumpBoost);
    }
    
}
