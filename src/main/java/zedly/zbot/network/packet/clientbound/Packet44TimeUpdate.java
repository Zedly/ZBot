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
public class Packet44TimeUpdate implements ClientBoundPacket {
    private long ageOfTheWorld;
    private long timeOfDay;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        ageOfTheWorld = dis.readLong();
        timeOfDay = dis.readLong();
    }

    public long getAgeOfTheWorld() {
        return ageOfTheWorld;
    }

    public long getTimeOfDay() {
        return timeOfDay;
    }
    
}
