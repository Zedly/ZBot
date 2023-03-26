package     zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.*;
import zedly.zbot.event.entity.EntityArrowHitEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * @author Dennis
 */

/**
* Used for a wide variety of game state things, from whether to bed use to gamemode to demo messages.
*/


/**
* Used for a wide variety of game state things, from weather to bed use to gamemode to demo messages.
*/

public class Packet1EChangeGameState implements ClientBoundPacket {
    private int reason;  // See below.
    private double value;  // Depends on Reason.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        reason = dis.readUnsignedByte();
        value = dis.readFloat();
    }

}
