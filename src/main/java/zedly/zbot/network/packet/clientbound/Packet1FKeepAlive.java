package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.KeepAliveEvent;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.packet.serverbound.Packet0BKeepAlive;

import java.io.IOException;

public class Packet1FKeepAlive implements ClientBoundPacket {

    int keepAliveId;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        keepAliveId = dis.readVarInt();
    }

    @Override
    public void process(GameContext context) {
        context.getUpThread().sendPacket(new Packet0BKeepAlive(keepAliveId));
        context.getMainThread().fireEvent(new KeepAliveEvent(keepAliveId));
    }
}
