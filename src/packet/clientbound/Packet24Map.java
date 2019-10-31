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
public class Packet24Map implements ClientBoundPacket {
    private int itemDamage;
    private int scale;
    private boolean tracking;
    private byte[][] icons;
    private byte columns;
    private byte rows;
    private byte x;
    private byte z;
    private int[] data;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        itemDamage = dis.readVarInt();
        scale = dis.readByte();
        tracking = dis.readBoolean();
        int iconCount = dis.readVarInt();
        icons = new byte[iconCount][3];
        for (int i = 0; i < iconCount; i++) {
            icons[i][0] = dis.readByte();
            icons[i][1] = dis.readByte();
            icons[i][2] = dis.readByte();
        }
        columns = dis.readByte();
        if (columns != 0) {
            rows = dis.readByte();
            x = dis.readByte();
            z = dis.readByte();
            int len = dis.readVarInt();
            data = new int[len];
            for (int i = 0; i < len; i++) {
                data[i] = dis.readUnsignedByte();
            }
        }
    }

    public int getItemDamage() {
        return itemDamage;
    }

    public int getScale() {
        return scale;
    }

    public byte[][] getIcons() {
        return icons;
    }

    public byte getColumns() {
        return columns;
    }

    public byte getRows() {
        return rows;
    }

    public byte getX() {
        return x;
    }

    public byte getZ() {
        return z;
    }

    public int[] getData() {
        return data;
    }

    public boolean isTracking() {
        return tracking;
    }
    
}
//Refactored ancestor. Review data strcuture