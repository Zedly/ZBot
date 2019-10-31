package   zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* Used to <i>visually</i> update whether boat paddles are turning.  The server will update the <a href="/Entities#Boat" class="mw-redirect" title="Entities">Boat entity metadata</a> to match the values here.
*/


/**
* Used to <i>visually</i> update whether boat paddles are turning.  The server will update the <a href="/Entities#Boat" class="mw-redirect" title="Entities">Boat entity metadata</a> to match the values here.
*/

public class Packet16SteerBoat implements ServerBoundPacket {
    private final boolean leftpaddleturning;
    private final boolean rightpaddleturning;


    public Packet16SteerBoat(boolean leftpaddleturning, boolean rightpaddleturning) {
        this.leftpaddleturning = leftpaddleturning;
        this.rightpaddleturning = rightpaddleturning;
    }

    @Override
    public int opCode() {
        return 0x16;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeBoolean(leftpaddleturning);
        dos.writeBoolean(rightpaddleturning);
    }
}
