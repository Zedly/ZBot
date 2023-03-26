package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.ClientSettings;
import zedly.zbot.ChatMode;
import zedly.zbot.network.ExtendedDataOutputStream;

/**
 *
 * @author Dennis
 */
/**
 * Sent when the player connects, or when settings are changed.
 */
/**
 * Sent when the player connects, or when settings are changed.
 */
public class Packet08ClientInformation implements ServerBoundPacket {

    private final String locale;  // e.g. <code>en_GB</code>.
    private final int viewDistance;  // Client-side render distance, in chunks.
    private final int chatMode;  // 0: enabled, 1: commands only, 2: hidden.  See <a href="/Chat#Processing_chat" title="Chat">processing chat</a> for more information.
    private final boolean chatColors;  // “Colors” multiplayer setting. Can the chat be colored?
    private final int displayedSkinParts;  // Bit mask, see below.
    private final int mainHand;  // 0: Left, 1: Right.
    private final boolean enabletextfiltering;  // Enables filtering of text on signs and written book titles. Currently always false (i.e. the filtering is disabled)
    private final boolean allowserverlistings;  // Servers usually list online players, this option should let you not show up in that list.

    public Packet08ClientInformation(ClientSettings clientSettings) {
        this(clientSettings.getLocale(),
                clientSettings.getViewDistance(),
                clientSettings.getChatMode().ordinal(),
                clientSettings.isChatColorsEnabled(),
                clientSettings.getSkinFlags(),
                clientSettings.isLeftHanded() ? 0 : 1,
                clientSettings.isEnableTextFilterring(),
                clientSettings.isAllowServerListings()
        );
    }

    public Packet08ClientInformation(String locale, int viewDistance, int chatMode, boolean chatColors, int displayedSkinParts, int mainHand, boolean enabletextfiltering, boolean allowserverlistings) {
        this.locale = locale;
        this.viewDistance = viewDistance;
        this.chatMode = chatMode;
        this.chatColors = chatColors;
        this.displayedSkinParts = displayedSkinParts;
        this.mainHand = mainHand;
        this.enabletextfiltering = enabletextfiltering;
        this.allowserverlistings = allowserverlistings;
    }

    @Override
    public int opCode() {
        return 0x08;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeString(locale);
        dos.writeByte(viewDistance);
        dos.writeVarInt(chatMode);
        dos.writeBoolean(chatColors);
        dos.writeByte(displayedSkinParts);
        dos.writeVarInt(mainHand);
        dos.writeBoolean(enabletextfiltering);
        dos.writeBoolean(allowserverlistings);
    }
}
