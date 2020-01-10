package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* This is sent to the client when it should create a new <span class="plainlinks"><a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Scoreboard">scoreboard</a></span> objective or remove one.
*/

public class Packet42ScoreboardObjective implements ClientBoundPacket {
    private String objectiveName;  // An unique name for the objective
    private int mode;  // 0 to create the scoreboard. 1 to remove the scoreboard. 2 to update the display text.
    private String objectiveValue;  // Only if mode is 0 or 2. The text to be displayed for the score
    private String type;  // Only if mode is 0 or 2. “integer” or “hearts”


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        objectiveName = dis.readString();
        mode = dis.readByte();
        objectiveValue = dis.readString();
        type = dis.readString();
    }

}
