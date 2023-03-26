package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;

public class Packet25SeenAdvancements implements ServerBoundPacket {

    private final int action;
    private final String tabID;

    public Packet25SeenAdvancements(String tabID) {
        action = 0;
        this.tabID = tabID;
    }

    public Packet25SeenAdvancements() {
        action = 1;
        tabID = null;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(action);
        if(action == 0) {
            dos.writeString(tabID);
        }
    }

    @Override
    public int opCode() {
        return 0x25;
    }

}

// Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture