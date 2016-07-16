/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.HashMap;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet07Statistics implements ClientBoundPacket {
    private int count;
    private HashMap<String, Integer> updates;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        updates = new HashMap<>();
        count = dis.readVarInt();
        for (int i = 0; i < count; i++) {
            updates.put(dis.readString(), dis.readVarInt());
        }
    }
}
