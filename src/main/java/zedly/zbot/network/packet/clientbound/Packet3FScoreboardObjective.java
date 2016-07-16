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
public class Packet3FScoreboardObjective implements ClientBoundPacket {
    private String objectiveName;
    private byte mode;
    private String objectiveValue;
    private String objectiveType;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        objectiveName = dis.readString();
        mode = dis.readByte();
        if (mode == 0 || mode == 2) {
            objectiveValue = dis.readString();
            objectiveType = dis.readString();
        }
    }
}
