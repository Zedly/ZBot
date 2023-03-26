package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
/**
* */

public class Packet45ServerData implements ClientBoundPacket {
    private String mOTD;
    private boolean hasIcon;
    private String icon;  // Icon PNG base64 String
    private boolean enforcesSecureChat;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        mOTD = dis.readString();
        hasIcon = dis.readBoolean();
        icon = dis.readString();
        enforcesSecureChat = dis.readBoolean();
    }

}
