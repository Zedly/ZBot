/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.entity.CraftEntityMeta.ParticleMeta;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet26Particle implements ClientBoundPacket {

    private int particleId;
    private boolean longDistance;
    private double x;
    private double y;
    private double z;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float particleData;
    private int numberOfParticles;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        particleId = dis.readInt();
        longDistance = dis.readBoolean();
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        offsetX = dis.readFloat();
        offsetY = dis.readFloat();
        offsetZ = dis.readFloat();
        particleData = dis.readFloat();
        numberOfParticles = dis.readInt();
        ParticleMeta pm = new ParticleMeta(particleId);
        pm.readOptionalMeta(dis);
    }

    public int getParticleId() {
        return particleId;
    }

    public boolean isLongDistance() {
        return longDistance;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
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
}
//Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture
// Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture