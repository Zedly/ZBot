/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * @author Dennis
 */
public class Packet46UpdateSign implements ClientBoundPacket {

    private int x;
    private int y;
    private int z;
    private String line1;
    private String line2;
    private String line3;
    private String line4;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        Location loc = dis.readPosition();
        x = loc.getBlockX();
        y = loc.getBlockY();
        z = loc.getBlockZ();
        line1 = dis.readString();
        line2 = dis.readString();
        line3 = dis.readString();
        line4 = dis.readString();
    }

}
