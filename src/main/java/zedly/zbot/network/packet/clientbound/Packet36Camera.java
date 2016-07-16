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
public class Packet36Camera implements ClientBoundPacket {
    private int cameraId;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        cameraId = dis.readVarInt();
    }

    public int getCameraId() {
        return cameraId;
    }
    
}
