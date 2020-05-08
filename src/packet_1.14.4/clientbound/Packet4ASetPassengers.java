/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * @author Dennis
 */
public class Packet4ASetPassengers implements ClientBoundPacket {

    private int vehicleId;
    private int[] passengers;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        vehicleId = dis.readVarInt();
        int length = dis.readVarInt();
        passengers = new int[length];
        for (int i = 0; i < length; i++) {
            passengers[i] = dis.readVarInt();
        }
    }
}
//Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture