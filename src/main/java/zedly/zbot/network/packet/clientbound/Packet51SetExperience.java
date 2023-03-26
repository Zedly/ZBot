package    zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
/**
 * Sent by the server when the client should change experience levels.
 */


/**
* Sent by the server when the client should change experience levels.
*/


/**
* Sent by the server when the client should change experience levels.
*/

public class Packet51SetExperience implements ClientBoundPacket {
    private double experiencebar;  // Between 0 and 1.
    private int level;
    private int totalExperience;  // See <span class="plainlinks"><a rel="nofollow" class="external text" href="https://minecraft.fandom.com/wiki/Experience%23Leveling_up">Experience#Leveling up</a></span> on the Minecraft Wiki for Total Experience to Level conversion.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        experiencebar = dis.readFloat();
        level = dis.readVarInt();
        totalExperience = dis.readVarInt();
    }

}
