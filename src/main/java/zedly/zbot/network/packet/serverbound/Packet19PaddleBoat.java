package     zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* Sent when the player's arm swings.
*/



/**
* Used to <i>visually</i> update whether boat paddles are turning.  The server will update the <a href="/Entity_metadata#Boat" title="Entity metadata">Boat entity metadata</a> to match the values here.
*/

public class Packet19PaddleBoat implements ServerBoundPacket {
    private final boolean leftpaddleturning;
    private final boolean rightpaddleturning;


    public Packet19PaddleBoat(boolean leftpaddleturning, boolean rightpaddleturning) {
        this.leftpaddleturning = leftpaddleturning;
        this.rightpaddleturning = rightpaddleturning;
    }

    @Override
    public int opCode() {
        return 0x19;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeBoolean(leftpaddleturning);
        dos.writeBoolean(rightpaddleturning);
    }
}
