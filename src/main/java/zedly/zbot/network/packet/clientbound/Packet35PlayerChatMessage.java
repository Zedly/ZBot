package  zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.Util;
import zedly.zbot.event.ChatEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import java.util.UUID;
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

public class Packet35PlayerChatMessage implements ClientBoundPacket {
    private UUID senderUUID;
    private int index;  // 0: chat (chat box), 1: system message (chat box), 2: game info (above hotbar).
    private byte[] messageSignature;
    private String jSONData;  // Limited to 32767 bytes
    private long timestamp;
    private long salt;
    private String unsignedContent;
    int filterType;
    long[] filterTypeBits;
    int targetChatType;
    String networkName;
    String networkTargetName;
    

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        senderUUID = dis.readUUID();
        index = dis.readVarInt();
        boolean signaturePresent = dis.readBoolean();
        if(signaturePresent) {
            messageSignature = new byte[256];
            dis.readFully(messageSignature);
        }
        jSONData = dis.readString();
        timestamp = dis.readLong();
        salt = dis.readLong();
        
        int numPreviousMessages = dis.readVarInt();
        for(int i = 0; i < numPreviousMessages; i++) {
            dis.readVarInt();
            dis.skip(256); // Previous messages with signatures
        }
        boolean unfilteredContentPresent = dis.readBoolean();
        if(unfilteredContentPresent) {
            unsignedContent = dis.readString();
            filterType = dis.readVarInt();
            if(filterType == 2) { // Partially filtered
                filterTypeBits = dis.readBitField();
            }
        }
        targetChatType = dis.readVarInt();
        networkName = dis.readString();
        boolean networkTargetPresent = dis.readBoolean();
        if(networkTargetPresent) {
            networkTargetName = dis.readString();
        }
    }

    @Override
    public void process(GameContext context) {
        String interpretedMessage = Util.interpretJson(jSONData);
        if (context.getClientConfig().getBoolean("ansi", false)) {
            System.out.println(Util.interpretJsonAsANSI(jSONData) + ChatColor.WHITE.getANSICode());
        } else {
            System.out.println(Util.interpretJson(jSONData));
        }
        context.getMainThread().fireEvent(new ChatEvent(jSONData, interpretedMessage));
    }

}