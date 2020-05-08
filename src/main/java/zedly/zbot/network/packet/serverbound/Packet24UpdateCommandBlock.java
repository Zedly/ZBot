package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataOutputStream;

public class Packet24UpdateCommandBlock implements ServerBoundPacket {

    private final Location location;
    private final String command;
    private final int mode;
    private final int flags;
    
    public Packet24UpdateCommandBlock(Location loc, String command, int mode, int flags) {
        this.location = loc;
        this.command = command;
        this.mode = mode;
        this.flags = flags;
    }
    
    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writePosition(location);
        dos.writeString(command);
        dos.writeVarInt(mode);
        dos.writeByte(flags);
    }

    @Override
    public int opCode() {
        return 0x24;
    }

}
// Refactored ancestor. Review data strcuture