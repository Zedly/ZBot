package   zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.Util;
import zedly.zbot.event.SelfKickEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;


/**
* Sent by the server before it disconnects a client. The client assumes that the server has already closed the connection by the time the packet arrives.
*/


/**
* The login process is as follows:
*/

public class Packet00Login implements ClientBoundPacket {
    private String reason;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        reason = dis.readString();
    }

}
