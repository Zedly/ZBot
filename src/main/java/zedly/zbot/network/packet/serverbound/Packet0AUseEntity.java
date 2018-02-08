/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataOutputStream;

/**
 *
 * @author Dennis
 */
public class Packet0AUseEntity implements ServerBoundPacket {
    int targetId;
    int clickType;
    int hand;
    Location loc;

    public Packet0AUseEntity(int targetId, int hand) {
        this.targetId = targetId;
        this.hand = hand;
        this.clickType = 0;
    }

    public Packet0AUseEntity(int targetId) {
        this.targetId = targetId;
        this.clickType = 1;
    }

    public Packet0AUseEntity(int targetId, Location loc, int hand) {
        this.targetId = targetId;
        this.clickType = 2;
        this.loc = loc;
        this.hand = hand;
    }

    @Override
    public int opCode() {
        return 0x0A;
    }
    
    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(targetId);
        dos.writeVarInt(clickType);
        if (clickType == 2) {
            dos.writeFloat((float) loc.getX());
            dos.writeFloat((float) loc.getY());
            dos.writeFloat((float) loc.getZ());
        }
        if (clickType != 1) {
            dos.writeVarInt(hand);
        }
    }
    
}
//Refactored ancestor. Review data strcuture