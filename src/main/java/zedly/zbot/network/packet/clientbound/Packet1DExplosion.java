/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.ExplosionEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * @author Dennis
 */
public class Packet1DExplosion implements ClientBoundPacket {

    private float x;
    private float y;
    private float z;
    private float radius;
    private int recordCount;
    private byte[][] records;
    private float playerMotionX;
    private float playerMotionY;
    private float playerMotionZ;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        x = dis.readFloat();
        y = dis.readFloat();
        z = dis.readFloat();
        radius = dis.readFloat();
        recordCount = dis.readInt();
        records = new byte[recordCount][3];
        for (int i = 0; i < recordCount; i++) {
            records[i][0] = dis.readByte();
            records[i][1] = dis.readByte();
            records[i][2] = dis.readByte();
        }
        playerMotionX = dis.readFloat();
        playerMotionY = dis.readFloat();
        playerMotionZ = dis.readFloat();
    }

    @Override
    public void process(GameContext context) {
        context.getMainThread().fireEvent(new ExplosionEvent(x, y, z, radius, records, playerMotionX, playerMotionY, playerMotionZ));
    }
}
//Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture