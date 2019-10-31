package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;

public class Packet1DRecipeBookData implements ServerBoundPacket {

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
    }

    @Override
    public int opCode() {
        return 0x1D;
    }

}

// This packet has never been implemented. If you find this in a stack trace: Congratulations, you played yourself