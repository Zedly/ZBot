/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.commons.lang3.tuple.Triple;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet05AwardStatistics implements ClientBoundPacket {

    private int count;
    private LinkedList<Triple<Integer, Integer, Integer>> updates = new LinkedList<>();

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        count = dis.readVarInt();
        for (int i = 0; i < count; i++) {
            updates.add(Triple.of(dis.readVarInt(), dis.readVarInt(), dis.readVarInt()));
        }
    }
}