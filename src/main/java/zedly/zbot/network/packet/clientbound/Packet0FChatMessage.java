package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.Util;
import zedly.zbot.api.event.ChatEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

public class Packet0FChatMessage implements ClientBoundPacket {

    private String message;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        message = Util.interpretJson(dis.readString());
        dis.readByte();
    }

    @Override
    public void process(GameContext context) {
        System.out.println(message.replaceAll("§.", ""));
        context.getMainThread().fireEvent(new ChatEvent(message));
    }
}
