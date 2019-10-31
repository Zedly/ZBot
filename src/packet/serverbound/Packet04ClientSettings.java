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
public class Packet04ClientSettings implements ServerBoundPacket {

    private final String locale;  // e.g. en_GB
    private final int viewDistance;  // Client-side render distance, in chunks
    private final int chatMode;  // 0: enabled, 1: commands only, 2: hidden.  See <a href="/Chat#Processing_chat" title="Chat">processing chat</a> for more information.
    private final boolean chatColors;  // “Colors” multiplayer setting
    private final int displayedSkinParts;  // Bit mask, see below
    private final int mainHand;  // 0: Left, 1: Right

    public Packet04ClientSettings(String locale, int viewDistance, int chatMode, boolean chatColors, int displayedSkinParts, int mainHand) {
        this.locale = locale;
        this.viewDistance = viewDistance;
        this.chatMode = chatMode;
        this.chatColors = chatColors;
        this.displayedSkinParts = displayedSkinParts;
        this.mainHand = mainHand;
    }

    public Packet04ClientSettings(ClientSettings clientSettings) {
        this(clientSettings.getLocale(), clientSettings.getViewDistance(), clientSettings.getChatMode().ordinal(), clientSettings.isChatColorsEnabled(), clientSettings.getSkinFlags(), clientSettings.isLeftHanded() ? 0 : 1);
    }

    @Override
    public int opCode() {
        return 0x04;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeString(locale);
        dos.writeByte(viewDistance);
        dos.writeVarInt(chatMode);
        dos.writeBoolean(chatColors);
        dos.writeByte(displayedSkinParts);
        dos.writeVarInt(mainHand);
    }
}
