package   zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.HealthChangeEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * @author Dennis
 */

/**
* Sent by the server to update/set the health of the player it is sent to.
*/


/**
* Sent by the server to update/set the health of the player it is sent to.
*/

public class Packet48UpdateHealth implements ClientBoundPacket {
    private double health;  // 0 or less = dead, 20 = full HP
    private int food;  // 0–20
    private double foodSaturation;  // Seems to vary from 0.0 to 5.0 in integer increments


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        health = dis.readFloat();
        food = dis.readVarInt();
        foodSaturation = dis.readFloat();
    }

}
