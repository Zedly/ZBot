/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.ClientSettings;
import zedly.zbot.ChatMode;
import zedly.zbot.network.ExtendedDataOutputStream;

/**
 *
 * @author Dennis
 */
public class Packet04ClientSettings implements ServerBoundPacket {
    private final String locale;
    private final int viewDistance;
    private final ChatMode chatMode;
    private final boolean chatColors;
    private final int showParts;
    private final int mainHand;

    public Packet04ClientSettings(ClientSettings clientSettings) {
        this.locale = clientSettings.getLocale();
        this.viewDistance = clientSettings.getViewDistance();
        this.chatMode = clientSettings.getChatMode();
        this.chatColors = clientSettings.isChatColorsEnabled();
        this.showParts = clientSettings.getSkinFlags();
        this.mainHand = clientSettings.isLeftHanded() ? 1 : 0;
    }

    @Override
    public int opCode() {
        return 0x04;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeString(locale);
        dos.writeByte(viewDistance);
        dos.writeVarInt(chatMode.ordinal());
        dos.writeBoolean(chatColors);
        dos.writeByte(showParts);
        dos.writeVarInt(mainHand);
    }
    
}
