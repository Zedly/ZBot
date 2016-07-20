package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.ServerDifficultyEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

public class Packet0DServerDifficulty implements ClientBoundPacket {

    private int difficulty;
    
    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        difficulty = dis.readUnsignedByte();
    }

    @Override
    public void process(GameContext context) {
        context.getMainThread().fireEvent(new ServerDifficultyEvent(difficulty));
    }
}
