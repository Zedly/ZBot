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
public class Packet21Effect implements ClientBoundPacket {

    private int effectID;
    private int x;
    private int y;
    private int z;
    private int data;
    private boolean disableRelativeVolume;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        effectID = dis.readInt();
        Location loc = dis.readPosition();
        x = loc.getBlockX();
        y = loc.getBlockY();
        z = loc.getBlockZ();
        data = dis.readInt();
        disableRelativeVolume = dis.readBoolean();
    }

    public int getEffectID() {
        return effectID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getData() {
        return data;
    }

    public boolean isDisableRelativeVolume() {
        return disableRelativeVolume;
    }

}
