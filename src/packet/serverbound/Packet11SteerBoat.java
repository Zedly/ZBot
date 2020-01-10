package  zedly.zbot.network.packet.serverbound;

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

public class Packet11SteerBoat implements ServerBoundPacket {
    private final boolean rightpaddleturning;
    private final boolean leftpaddleturning;


    public Packet11SteerBoat(boolean rightpaddleturning, boolean leftpaddleturning) {
        this.rightpaddleturning = rightpaddleturning;
        this.leftpaddleturning = leftpaddleturning;
    }

    @Override
    public int opCode() {
        return 0x11;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeBoolean(rightpaddleturning);
        dos.writeBoolean(leftpaddleturning);
    }
}
