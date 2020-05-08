package    zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* This is sent to the client when it should display a scoreboard.
*/

public class Packet43DisplayScoreboard implements ClientBoundPacket {
    private int position;  // The position of the scoreboard. 0: list, 1: sidebar, 2: below name, 3 - 18: team specific sidebar, indexed as 3 + team color.
    private String scoreName;  // The unique name for the scoreboard to be displayed.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        position = dis.readByte();
        scoreName = dis.readString();
    }

}
