package  zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.Util;
import zedly.zbot.event.ChatEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import zedly.zbot.ChatColor;

/**
 * Identifying the difference between Chat/System Message is important as it
 * helps respect the user's chat visibility options. See
 * <a href="/Chat#Processing_chat" title="Chat">processing chat</a> for more
 * info about these positions.
 */

/**
* Identifying the difference between Chat/System Message is important as it helps respect the user's chat visibility options.  See <a href="/Chat#Processing_chat" title="Chat">processing chat</a> for more info about these positions.
*/

public class Packet19DeleteMessage implements ClientBoundPacket {
    private byte[] signature;
    
    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        int length = dis.readVarInt();
        signature = new byte[length];
        dis.readFully(signature);
    }
}