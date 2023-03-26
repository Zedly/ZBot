package     zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.ServerDifficultyEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;


/**
* Changes the difficulty setting in the client's option menu
*/


/**
* Changes the difficulty setting in the client's option menu
*/

public class Packet0CChangeDifficulty implements ClientBoundPacket {
    private int difficulty;  // 0: peaceful, 1: easy, 2: normal, 3: hard.
    private boolean difficultylocked?;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        difficulty = dis.readUnsignedByte();
        difficultylocked? = dis.readBoolean();
    }

}
