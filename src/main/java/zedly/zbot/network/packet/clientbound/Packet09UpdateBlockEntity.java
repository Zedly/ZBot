/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.Location;
import net.minecraft.server.NBTBase;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet09UpdateBlockEntity implements ClientBoundPacket {
    private int x;
    private int y;
    private int z;
    private int action;
    private NBTBase nbt;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        Location loc = dis.readPosition();
        x = loc.getBlockX();
        y = loc.getBlockY();
        z = loc.getBlockZ();
        action = dis.readUnsignedByte();
        nbt = dis.readNBT();
    }
}
