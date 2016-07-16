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
public class Packet48CollectItem implements ClientBoundPacket {
    private int collectedEntityID;
    private int collectorEntityID;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        collectedEntityID = dis.readVarInt();
        collectorEntityID = dis.readVarInt();
    }
}
