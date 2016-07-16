/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataOutputStream;

/**
 *
 * @author Dennis
 */
public class Packet01TabComplete implements ServerBoundPacket {
    String text;
    boolean assumeCommand;
    Location loc;

    public Packet01TabComplete(String text) {
        this.text = text;
        assumeCommand = false;
    }

    public Packet01TabComplete(String text, boolean assumeCommand) {
        this.text = text;
        this.assumeCommand = assumeCommand;
    }

    public Packet01TabComplete(String text, boolean assumeCommand, Location loc) {
        this.text = text;
        this.assumeCommand = assumeCommand;
        this.loc = loc;
    }

    @Override
    public int opCode() {
        return 0x01;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeString(text);
        dos.writeBoolean(assumeCommand);
        dos.writeBoolean(loc != null);
        if (loc != null) {
            dos.writePosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        }
    }
    
}
