/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet08BlockBreakAnimation implements ClientBoundPacket {
    private int entityID;
    private int x;
    private int y;
    private int z;
    private byte destroyStage;
    
    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        Location loc = dis.readPosition();
        x = loc.getBlockX();
        y = loc.getBlockY();
        z = loc.getBlockZ();
        destroyStage = dis.readByte();
    }
}
