package  zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.Util;
import zedly.zbot.event.SelfKickEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;


/**
* Sent by the server before it disconnects a client. The client assumes that the server has already closed the connection by the time the packet arrives.
*/

public class Packet00Disconnect implements ClientBoundPacket {
    private String reason;  // Displayed to the client when the connection terminates.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        reason = dis.readString();
    }
    
    public String getReason() {
        return reason;
    }
    
    public String getFormattedReason() {
        String formattedReason = Util.interpretJson(reason);
        return formattedReason;
    }

}
