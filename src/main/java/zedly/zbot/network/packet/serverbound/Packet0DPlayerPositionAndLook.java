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
public class Packet0DPlayerPositionAndLook implements ServerBoundPacket {
    double x;
    double feetY;
    double z;
    double yaw;
    double pitch;
    boolean onGround;

    @Override
    public int opCode() {
        return 0x0D;
    }

    public Packet0DPlayerPositionAndLook(double x, double feetY, double z, float yaw, float pitch, boolean onGround) {
        this.x = x;
        this.feetY = feetY;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public Packet0DPlayerPositionAndLook(Location loc) {
        this.x = loc.getX();
        this.feetY = loc.getY();
        this.z = loc.getZ();
        this.yaw = loc.getYaw();
        this.pitch = loc.getPitch();
        this.onGround = true;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeDouble(x);
        dos.writeDouble(feetY);
        dos.writeDouble(z);
        dos.writeFloat((float) yaw);
        dos.writeFloat((float) pitch);
        dos.writeBoolean(onGround);
    }
    
}
