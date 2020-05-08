package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.packet.clientbound.ClientBoundPacket;

/**
 *
 * @author Dennis
 */
/**
 * Enables compression. If compression is enabled, all following packets are
 * encoded in the <a href="#With_compression">compressed packet format</a>.
 * Negative values will disable compression, meaning the packet format should
 * remain in the <a href="#Without_compression">uncompressed packet format</a>.
 * However, this packet is entirely optional, and if not sent, compression will
 * also not be enabled (the notchian server does not send the packet when
 * compression is disabled).
 */
public class Packet03SetCompression implements ClientBoundPacket {

    private int threshold;  // Maximum size of a packet before it is compressed

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        threshold = dis.readVarInt();
    }

    public int getThreshold() {
        return threshold;
    }

}
