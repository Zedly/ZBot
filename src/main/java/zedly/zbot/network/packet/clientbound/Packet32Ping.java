package     zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.KeepAliveEvent;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.packet.serverbound.Packet0FKeepAlive;

import java.io.IOException;


/**
* The server will frequently send out a keep-alive, each containing a random ID. The client must respond with the same packet. If the client does not respond to them for over 30 seconds, the server kicks the client. Vice versa, if the server does not send any keep-alives for 20 seconds, the client will disconnect and yields a "Timed out" exception.
*/


/**
* Packet is not used by the Notchian server. When sent to the client, client responds with a <a href="#Pong_.28play.29">Pong</a> packet with the same id.
*/

public class Packet32Ping implements ClientBoundPacket {
    private int iD;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        iD = dis.readInt();
    }

    @Override
    public void process(GameContext context) {
        context.getUpThread().sendPacket(new Packet0FKeepAlive(keepAliveID));
        context.getMainThread().fireEvent(new KeepAliveEvent(keepAliveID));    }

}
