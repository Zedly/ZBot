package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;
/**
* Used when <kbd>Shift</kbd>+<kbd>F3</kbd>+<kbd>I</kbd> is pressed while looking at an entity.
*/

public class Packet0DQueryEntityNBT implements ServerBoundPacket {
    private final int transactionID;  // An incremental ID so that the client can verify that the response matches.
    private final int entityID;  // The ID of the entity to query.


    public Packet0DQueryEntityNBT(int transactionID, int entityID) {
        this.transactionID = transactionID;
        this.entityID = entityID;
    }

    @Override
    public int opCode() {
        return 0x0D;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(transactionID);
        dos.writeVarInt(entityID);
    }
}
