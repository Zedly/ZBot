package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;
/**
* When a player selects a specific trade offered by a villager NPC.
*/

public class Packet21SelectTrade implements ServerBoundPacket {
    private final int selectedslot;  // The selected slot int the players current (trading) inventory. (Was a full Integer for the plugin message)


    public Packet21SelectTrade(int selectedslot) {
        this.selectedslot = selectedslot;
    }

    @Override
    public int opCode() {
        return 0x21;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(selectedslot);
    }
}
