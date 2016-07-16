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
public class Packet19NamedSoundEffect implements ClientBoundPacket {
    private String soundName;
    private int soundCategory;
    private int x;
    private int y;
    private int z;
    private float volume;
    private float pitch;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        soundName = dis.readString();
        soundCategory = dis.readVarInt();
        x = dis.readInt();
        y = dis.readInt();
        z = dis.readInt();
        volume = dis.readFloat();
        pitch = dis.readFloat();
    }
}
