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
/**
 * Unused by the Notchian server. Likely provided for custom servers to send
 * chat message completions to clients.
 */
public class Packet16ChatSuggestions implements ClientBoundPacket {

    private int action;  // 0: Add, 1: Remove, 2: Set
    private int count;  // Number of elements in the following array.
    private String[] entries;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        action = dis.readVarInt();
        count = dis.readVarInt();
        for (int i = 0; i < count; i++) {
            entries[i] = dis.readString();
        }
    }

    @Override
    public void process(GameContext context) {
        context.getMainThread().fireEvent(new TabCompleteEvent(entries));
    }

}
