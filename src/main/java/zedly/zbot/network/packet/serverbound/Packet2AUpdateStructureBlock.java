package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;

public class Packet2AUpdateStructureBlock implements ServerBoundPacket {
    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {}

    @Override
    public int opCode() { return 0x2A; }

}

// This packet has never been implemented. If you find this in a stack trace: Congratulations, you played yourselfRefactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture