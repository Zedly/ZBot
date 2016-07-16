package zedly.zbot.network;

import zedly.zbot.network.packet.clientbound.ClientBoundPacket;
import java.io.IOException;
import java.io.InputStream;

public class PacketInputStream extends ExtendedDataInputStream {
    
    private StreamState state;
    
    public PacketInputStream(InputStream is) {
        super(is);
    }
    
    public PacketInputStream(InputStream is, StreamState state) {
        super(is);
        this.state = state;
    }
    
    public ClientBoundPacket readPacket() throws IOException, InstantiationException, IllegalAccessException {
        int len = readVarInt();
        int op = readVarInt();
        ClientBoundPacket p = Packet.getInstanceClientBound(op, state);
        p.readPacket(this, len);
        return p;
    }  
}
