package     zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.event.WorldTimeChangeEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Time is based on ticks, where 20 ticks happen every second. There are 24000 ticks in a day, making Minecraft days exactly 20 minutes long.
*/


/**
* Time is based on ticks, where 20 ticks happen every second. There are 24000 ticks in a day, making Minecraft days exactly 20 minutes long.
*/

public class Packet5EUpdateTime implements ClientBoundPacket {
    private long worldAge;  // In ticks; not changed by server commands.
    private long timeofday;  // The world (or region) time, in ticks. If negative the sun will stop moving at the Math.abs of the time.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        worldAge = dis.readLong();
        timeofday = dis.readLong();
    }

    @Override
    public void process(GameContext context) {
        context.getEventDispatcher().dispatchEvent(new WorldTimeChangeEvent(timeofday, worldAge));
        context.getSelf().getEnvironment().setTimeOfDay(timeofday);
        context.getSelf().getEnvironment().setWorldAge(worldAge);    }

}
