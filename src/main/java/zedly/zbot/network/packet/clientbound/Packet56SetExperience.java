package   zedly.zbot.network.packet.clientbound;

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

public class Packet56SetExperience implements ClientBoundPacket {
    private double experiencebar;  // Between 0 and 1.
    private int totalExperience;  // See <span class="plainlinks"><a rel="nofollow" class="external text" href="https://minecraft.fandom.com/wiki/Experience%23Leveling_up">Experience#Leveling up</a></span> on the Minecraft Wiki for Total Experience to Level conversion.
    private int level;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        experiencebar = dis.readFloat();
        totalExperience = dis.readVarInt();
        level = dis.readVarInt();
    }

    @Override
    public void process(GameContext context) {
        context.getSelf().setExperience(level, experiencebar);    }

}
