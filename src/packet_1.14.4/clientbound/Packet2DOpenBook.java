package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
/**
* Sent when a player right clicks with a signed book. This tells the client to open the book GUI.
*/

public class Packet2DOpenBook implements ClientBoundPacket {
    private int hand;  // 0: Main hand, 1: Off hand


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        hand = dis.readVarInt();
    }

}
