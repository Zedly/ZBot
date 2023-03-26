package      zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.event.KeepAliveEvent;
import zedly.zbot.network.ExtendedDataInputStream;
import java.io.IOException;
import zedly.zbot.network.packet.serverbound.Packet0FKeepAlive;

/**
* The server will frequently send out a keep-alive, each containing a random ID. The client must respond with the same payload (see <a href="#Keep_Alive_.28serverbound.29">serverbound Keep Alive</a>). If the client does not respond to them for over 30 seconds, the server kicks the client. Vice versa, if the server does not send any keep-alives for 20 seconds, the client will disconnect and yields a "Timed out" exception.
*/

public class Packet21KeepAlive implements ClientBoundPacket {
    private long keepAliveID;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        keepAliveID = dis.readLong();
    }

    @Override
    public void process(GameContext context) {
        context.getUpThread().sendPacket(new Packet0FKeepAlive(keepAliveID));
        context.getMainThread().fireEvent(new KeepAliveEvent(keepAliveID));
    }

}