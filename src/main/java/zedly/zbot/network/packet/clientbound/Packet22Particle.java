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
public class Packet22Particle implements ClientBoundPacket {
    private int particleId;
    private boolean longDistance;
    private float x;
    private float y;
    private float z;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float particleData;
    private int numberOfParticles;
    private int[] data;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        particleId = dis.readInt();
        longDistance = dis.readBoolean();
        x = dis.readFloat();
        y = dis.readFloat();
        z = dis.readFloat();
        offsetX = dis.readFloat();
        offsetY = dis.readFloat();
        offsetZ = dis.readFloat();
        particleData = dis.readFloat();
        numberOfParticles = dis.readInt();
        switch (particleId) {
            case 36:
                data = new int[2];
                break;
            case 37:
            case 38:
                data = new int[1];
                break;
            default:
                data = new int[0];
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = dis.readVarInt();
        }
    }

    public int getParticleId() {
        return particleId;
    }

    public boolean isLongDistance() {
        return longDistance;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getOffsetZ() {
        return offsetZ;
    }

    public float getParticleData() {
        return particleData;
    }

    public int getNumberOfParticles() {
        return numberOfParticles;
    }

    public int[] getData() {
        return data;
    }
    
}
