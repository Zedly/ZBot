package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
/**
* */

public class Packet46SetActionBarText implements ClientBoundPacket {
    private String actionbartext;  // Displays a message above the hotbar (the same as position 2 in <a href="#Player_Chat_Message">Player Chat Message</a>.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        actionbartext = dis.readString();
    }

}