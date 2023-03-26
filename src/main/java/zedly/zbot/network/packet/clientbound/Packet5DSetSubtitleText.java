package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
/**
* */

public class Packet5DSetSubtitleText implements ClientBoundPacket {
    private String subtitleText;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        subtitleText = dis.readString();
    }

}
