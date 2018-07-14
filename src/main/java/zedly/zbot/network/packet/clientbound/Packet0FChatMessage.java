package  zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.Util;
import zedly.zbot.event.ChatEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;


/**
* Identifying the difference between Chat/System Message is important as it helps respect the user's chat visibility options.  
* See <a href="/Chat#Processing_chat" title="Chat">processing chat</a> for more info about these positions.
*/

public class Packet0FChatMessage implements ClientBoundPacket {
    private String jSONData;  // Limited to 32767 bytes
    private int position;  // 0: chat (chat box), 1: system message (chat box), 2: game info (above hotbar).


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        jSONData = dis.readString();
        position = dis.readByte();
    }

    @Override
    public void process(GameContext context) {
        String message = Util.interpretJson(jSONData);
        System.out.println(message.replaceAll("\u00A7.", ""));
        context.getMainThread().fireEvent(new ChatEvent(jSONData, message));
    }

}
