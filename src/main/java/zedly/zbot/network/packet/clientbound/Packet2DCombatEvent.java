/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet2DCombatEvent implements ClientBoundPacket {
    private int event;
    private int duration;
    private int playerId;
    private int entityId;
    private String message;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        event = dis.readVarInt();
        if (event == 1) {
            duration = dis.readVarInt();
            entityId = dis.readInt();
        } else if (event == 2) {
            playerId = dis.readVarInt();
            entityId = dis.readInt();
            message = dis.readString();
        }
    }
}
//Refactored ancestor. Review data strcuture