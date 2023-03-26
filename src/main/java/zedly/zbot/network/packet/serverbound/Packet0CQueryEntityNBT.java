package   zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* Used when <kbd>Shift</kbd>+<kbd>F3</kbd>+<kbd>I</kbd> is pressed while looking at a block.
*/


/**
* Used when <kbd>Shift</kbd>+<kbd>F3</kbd>+<kbd>I</kbd> is pressed while looking at an entity.
*/


/**
* Used when <kbd>Shift</kbd>+<kbd>F3</kbd>+<kbd>I</kbd> is pressed while looking at an entity.
*/

public class Packet0CQueryEntityNBT implements ServerBoundPacket {
    private final int transactionID;  // An incremental ID so that the client can verify that the response matches.
    private final int entityID;  // The ID of the entity to query.


    public Packet0CQueryEntityNBT(int transactionID, int entityID) {
        this.transactionID = transactionID;
        this.entityID = entityID;
    }

    @Override
    public int opCode() {
        return 0x0C;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(transactionID);
        dos.writeVarInt(entityID);
    }
}
