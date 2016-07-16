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
public class Packet33Respawn implements ClientBoundPacket {
    private int dimension;
    private int difficulty;
    private int gamemode;
    private String levelType;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        dimension = dis.readInt();
        difficulty = dis.readUnsignedByte();
        gamemode = dis.readUnsignedByte();
        levelType = dis.readString();
    }
}
