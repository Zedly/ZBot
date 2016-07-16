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
public class Packet19UpdateSign implements ServerBoundPacket {

    private final Location loc;
    private final String line0;
    private final String line1;
    private final String line2;
    private final String line3;

    public Packet19UpdateSign(Location loc, String line0, String line1, String line2, String line3) {
        this.loc = loc;
        this.line0 = line0;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
    }

    @Override
    public int opCode() {
        return 0x19;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writePosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        dos.writeString(line0);
        dos.writeString(line1);
        dos.writeString(line2);
        dos.writeString(line3);
    }

}
