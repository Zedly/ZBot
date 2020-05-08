package  zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.clientbound.ClientBoundPacket;

//Login, Client


/**
*/

public class Packet00LoginStart implements ServerBoundPacket {
    private final String name; // Player's username  
    

    public Packet00LoginStart(String name) {
        this.name = name;
    }

    @Override
    public int opCode() {
        return 0x00;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeString(name);
    }
}
