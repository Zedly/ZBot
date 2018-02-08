package  zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */

/**
* If a transaction sent by the client was not accepted, the server will reply with a <a href="#Confirm_Transaction_.28clientbound.29">Confirm Transaction (clientbound)</a> packet with the Accepted field set to false. When this happens, the client must send this packet to apologize (as with movement), otherwise the server ignores any successive transactions.
*/

public class Packet05ConfirmTransaction implements ServerBoundPacket {
    private final int windowID;  // The ID of the window that the action occurred in
    private final int actionNumber;  // Every action that is to be accepted has a unique number. This number is an incrementing integer (starting at 1) with separate counts for each window ID.
    private final boolean accepted;  // Whether the action was accepted


    public Packet05ConfirmTransaction(int windowID, int actionNumber, boolean accepted) {
        this.windowID = windowID;
        this.actionNumber = actionNumber;
        this.accepted = accepted;
    }

    @Override
    public int opCode() {
        return 0x05;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(windowID);
        dos.writeShort(actionNumber);
        dos.writeBoolean(accepted);
    }
}
