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
public class Packet3DWorldBorder implements ClientBoundPacket {
    private int action;
    private double radius;
    private double oldRadius;
    private long radialSpeed;
    private double centerX;
    private double centerZ;
    private int maxPortalRange;
    private int warningTime;
    private int warningBlocks;
    
    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        action = dis.readVarInt();
        switch (action) {
            case 0:
                radius = dis.readDouble();
                break;
            case 1:
                oldRadius = dis.readDouble();
                radius = dis.readDouble();
                radialSpeed = dis.readVarLong();
                break;
            case 2:
                centerX = dis.readDouble();
                centerZ = dis.readDouble();
                break;
            case 3:
                centerX = dis.readDouble();
                centerZ = dis.readDouble();
                oldRadius = dis.readDouble();
                radius = dis.readDouble();
                radialSpeed = dis.readVarLong();
                maxPortalRange = dis.readVarInt();
                warningTime = dis.readVarInt();
                warningBlocks = dis.readVarInt();
                break;
            case 4:
                warningTime = dis.readVarInt();
                break;
            case 5:
                warningBlocks = dis.readVarInt();
                break;
        }
    }
    
}
//Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture