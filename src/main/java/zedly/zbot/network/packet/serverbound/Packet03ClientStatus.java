package  zedly.zbot.network.packet.serverbound;

import zedly.zbot.network.ExtendedDataOutputStream;

import java.io.IOException;

/**
 * @author Dennis
 */

/**
* */

public class Packet03ClientStatus implements ServerBoundPacket {
    private final int actionID;  // See below


    public Packet03ClientStatus(int actionID) {
        this.actionID = actionID;
    }

    @Override
    public int opCode() {
        return 0x03;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(actionID);
    }
}
