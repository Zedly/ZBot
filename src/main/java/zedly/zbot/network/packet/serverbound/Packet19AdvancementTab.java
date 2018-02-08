package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;

public class Packet19AdvancementTab implements ServerBoundPacket {

    private final int action;
    private final String tabID;

    public Packet19AdvancementTab(String tabID) {
        action = 0;
        this.tabID = tabID;
    }

    public Packet19AdvancementTab() {
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
        return 0x19;
    }

}
//Skeleton with unreadable structure and no ancestor. Implement manually
