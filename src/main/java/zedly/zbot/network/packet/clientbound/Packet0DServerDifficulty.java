package  zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.ServerDifficultyEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;


/**
* Changes the difficulty setting in the client's option menu
*/

public class Packet0DServerDifficulty implements ClientBoundPacket {
    private int difficulty;  // 0: peaceful, 1: easy, 2: normal, 3: hard


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        difficulty = dis.readUnsignedByte();
    }

    @Override
    public void process(GameContext context) {
        context.getMainThread().fireEvent(new ServerDifficultyEvent(difficulty));    }

}
