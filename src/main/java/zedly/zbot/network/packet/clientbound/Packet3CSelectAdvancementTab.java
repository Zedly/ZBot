package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
/**
* Sent by the server to indicate that the client should switch advancement tab. Sent either when the client switches tab in the GUI or when an advancement in another tab is made.
*/


/**
* Sent by the server to indicate that the client should switch advancement tab. Sent either when the client switches tab in the GUI or when an advancement in another tab is made.
*/

public class Packet3CSelectAdvancementTab implements ClientBoundPacket {
    private boolean hasid;  // Indicates if the next field is present
    private String optionalIdentifier;  // See below


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        hasid = dis.readBoolean();
        optionalIdentifier = dis.readString();
    }

}
