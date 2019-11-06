package   zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.event.TransactionResponseEvent;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.packet.serverbound.Packet07ConfirmTransaction;

/**
 *
 * @author Dennis
 */

/**
* A packet from the server indicating whether a request from the client was accepted, or whether there was a conflict (due to lag).  If the packet was not accepted, the client must respond with a <a href="#Confirm_Transaction_.28serverbound.29">serverbound confirm transaction</a> packet.
*/


/**
* A packet from the server indicating whether a request from the client was accepted, or whether there was a conflict (due to lag).  If the packet was not accepted, the client must respond with a <a href="#Confirm_Transaction_.28serverbound.29">serverbound confirm transaction</a> packet.
*/

public class Packet12ConfirmTransaction implements ClientBoundPacket {
    private int windowID;  // The ID of the window that the action occurred in
    private int actionNumber;  // Every action that is to be accepted has a unique number. This number is an incrementing integer (starting at 0) with separate counts for each window ID.
    private boolean accepted;  // Whether the action was accepted


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readByte();
        actionNumber = dis.readShort();
        accepted = dis.readBoolean();
    }

    @Override
    public void process(GameContext context) {
        System.out.println("Transaction ID " + actionNumber + ": " + accepted);
        if(!accepted) {
            context.getUpThread().sendPacket(new Packet07ConfirmTransaction(windowID, actionNumber, accepted));
        }
        context.getEventDispatcher().dispatchEvent(new TransactionResponseEvent(windowID, actionNumber, accepted ? 1 : 0));
    }

}
