package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.TabCompleteEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * The server responds with a list of auto-completions of the last word sent to
 * it. In the case of regular chat, this is a player username. Command names and
 * parameters are also supported. The client sorts these alphabetically before
 * listing them.
 */
public class Packet0FCommandSuggestionsResponse implements ClientBoundPacket {

    private String[] matches;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        int id = dis.readVarInt();
        int start = dis.readVarInt();
        int length = dis.readVarInt();
        int count = dis.readVarInt();
        matches = new String[count];
        for (int i = 0; i < count; i++) {
            matches[i] = dis.readString();
            if (dis.readBoolean()) {
                dis.readString();
            }
        }
    }

    @Override
    public void process(GameContext context) {
        context.getMainThread().fireEvent(new TabCompleteEvent(matches));
    }

}

// Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture