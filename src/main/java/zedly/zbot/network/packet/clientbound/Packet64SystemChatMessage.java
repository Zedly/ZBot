package   zedly.zbot.network.packet.clientbound;

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


/**
* Identifying the difference between Chat/System Message is important as it helps respect the user's chat visibility options.  See <a href="/Chat#Processing_chat" title="Chat">processing chat</a> for more info about these positions.
*/

public class Packet64SystemChatMessage implements ClientBoundPacket {
    private String content;  // Limited to 262144 bytes.
    private boolean overlay;  // Whether the message is an actionbar or chat message.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        content = dis.readString();
        overlay = dis.readBoolean();
    }

    @Override
    public void process(GameContext context) {
        String message = Util.interpretJson(content);
        if (context.getClientConfig().getBoolean("ansi", false)) {
            System.out.println(Util.interpretJsonAsANSI(content) + ChatColor.WHITE.getANSICode());
        } else {
            System.out.println(Util.interpretJson(content));
        }
        context.getMainThread().fireEvent(new ChatEvent(content, message));
    }

}
