package zedly.zbot.network.packet.serverbound;
public class Packet06PlayerSession implements ServerBoundPacket {
    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {}

    @Override
    public int opCode() { return 0x06; }

} Skeleton with unreadable structure and no ancestor. Implement manually