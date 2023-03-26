package   zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

// Status, Serverbound


/**
* */


/**
* */

public class Packet01Ping implements ServerBoundPacket {
    private final long payload;  // May be any number. Notchian clients use a system-dependent time value which is counted in milliseconds.


    public Packet01Ping(long payload) {
        this.payload = payload;
    }

    @Override
    public int opCode() {
        return 0x01;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeLong(payload);
    }
}
