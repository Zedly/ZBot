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
public class Packet17SetCooldown implements ClientBoundPacket {
    private int itemId;
    private int cooldownTicks;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        itemId = dis.readVarInt();
        cooldownTicks = dis.readVarInt();
    }
}
