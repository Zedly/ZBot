package      zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.KeepAliveEvent;
import zedly.zbot.network.ExtendedDataInputStream;
import java.io.IOException;
import zedly.zbot.network.packet.serverbound.Packet1DPong;

/**
* Packet is not used by the Notchian server. When sent to the client, client responds with a <a href="#Pong_.28play.29">Pong</a> packet with the same id.
*/


/**
* Packet is not used by the Notchian server. When sent to the client, client responds with a <a href="#Pong_2">Pong</a> packet with the same id.
*/

public class Packet30Ping implements ClientBoundPacket {
    private int iD;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        iD = dis.readInt();
    }

    @Override
    public void process(GameContext context) {
        context.getUpThread().sendPacket(new Packet1DPong(iD));
        context.getMainThread().fireEvent(new KeepAliveEvent(iD));
    }

}
