package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

public interface ClientBoundPacket {

    default void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        byte[] data = new byte[packetLen];
        dis.read(data);
    }

    default void process(GameContext context) {
    }
}
