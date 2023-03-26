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
* Used to send system chat messages to the client.
*/

public class Packet1BDisguisedChatMessage implements ClientBoundPacket {
    private String message;
    private int chatType;  // The chat message type.
    private String chatTypeName;  // The name associated with the chat type. Usually the message sender's display name.
    private boolean hasTargetName;  // True if target name is present.
    private String targetName;  // The target name associated with the chat type. Usually the message target's display name. Only present if previous boolean is true.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        message = dis.readString();
        chatType = dis.readVarInt();
        chatTypeName = dis.readString();
        hasTargetName = dis.readBoolean();
        targetName = dis.readString();
    }

    @Override
    public void process(GameContext context) {
        String interpretedMessage = Util.interpretJson(message);
        if (context.getClientConfig().getBoolean("ansi", false)) {
            System.out.println(Util.interpretJsonAsANSI(message) + ChatColor.WHITE.getANSICode());
        } else {
            System.out.println(Util.interpretJson(message));
        }
        context.getMainThread().fireEvent(new ChatEvent(message, interpretedMessage));
    }

}
