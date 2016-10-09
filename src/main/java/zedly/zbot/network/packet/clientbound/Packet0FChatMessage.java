package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.ChatEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import zedly.zbot.StringUtil;

public class Packet0FChatMessage implements ClientBoundPacket {

    private String message;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        message = StringUtil.interpretJson(dis.readString());
        dis.readByte();
    }

    @Override
    public void process(GameContext context) {
        System.out.println(message.replaceAll("§.", ""));
        context.getMainThread().fireEvent(new ChatEvent(message));
    }
}
