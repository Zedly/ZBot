package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

public class Packet11DeclareCommands implements ClientBoundPacket {

    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        while((packetLen -= dis.skip(packetLen)) > 0) {
            // Fuck you Dinnerbone
        }
    }

}