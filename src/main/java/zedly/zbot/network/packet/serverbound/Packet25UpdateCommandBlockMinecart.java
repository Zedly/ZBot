package  zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* */


/**
* */

public class Packet25UpdateCommandBlockMinecart implements ServerBoundPacket {
    private final int entityID;
    private final String command;
    private final boolean trackOutput;  // If false, the output of the previous command will not be stored within the command block.


    public Packet25UpdateCommandBlockMinecart(int entityID, String command, boolean trackOutput) {
        this.entityID = entityID;
        this.command = command;
        this.trackOutput = trackOutput;
    }

    @Override
    public int opCode() {
        return 0x25;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(entityID);
        dos.writeString(command);
        dos.writeBoolean(trackOutput);
    }
}
