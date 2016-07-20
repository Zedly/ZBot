package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.TabCompleteEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

public class Packet0ETabComplete implements ClientBoundPacket {

    private String[] options;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        int count = dis.readVarInt();
        options = new String[count];
        for (int i = 0; i < count; i++) {
            options[i] = dis.readString();
        }
    }

    @Override
    public void process(GameContext context) {
        context.getMainThread().fireEvent(new TabCompleteEvent(options));
    }
}
