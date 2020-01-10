package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* Used when <kbd>Shift</kbd>+<kbd>F3</kbd>+<kbd>I</kbd> is pressed while looking at a block.
*/

public class Packet01QueryBlockNBT implements ServerBoundPacket {
    private final int transactionID;  // An incremental ID so that the client can verify that the response matches.
    private final Location location;  // The location of the block to check.


    public Packet01QueryBlockNBT(int transactionID, Location location) {
        this.transactionID = transactionID;
        this.location = location;
    }

    @Override
    public int opCode() {
        return 0x01;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(transactionID);
        dos.writePosition(location);
    }
}
