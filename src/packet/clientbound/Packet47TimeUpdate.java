package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Time is based on ticks, where 20 ticks happen every second. There are 24000 ticks in a day, making Minecraft days exactly 20 minutes long.
*/

public class Packet47TimeUpdate implements ClientBoundPacket {
    private long worldAge;  // In ticks; not changed by server commands
    private long timeofday;  // The world (or region) time, in ticks. If negative the sun will stop moving at the Math.abs of the time


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        worldAge = dis.readLong();
        timeofday = dis.readLong();
    }

}
