package zedly.zbot.network.packet.clientbound;

import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

public class Packet32ResourcePackSend implements ClientBoundPacket {

    private String url;
    private String hash;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        url = dis.readString();
        hash = dis.readString();
    }
}
