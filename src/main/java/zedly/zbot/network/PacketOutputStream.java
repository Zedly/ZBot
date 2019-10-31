package zedly.zbot.network;

import zedly.zbot.network.packet.serverbound.ServerBoundPacket;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PacketOutputStream extends ExtendedDataOutputStream {

    private StreamState state;
    private ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
    private ExtendedDataOutputStream dataBuffer = new ExtendedDataOutputStream(byteBuffer);

    public PacketOutputStream(OutputStream os) {
        super(os);
    }

    public PacketOutputStream(OutputStream os, StreamState state) {
        super(os);
        this.state = state;
    }

    public void writePacket(ServerBoundPacket p) throws IOException {
        byteBuffer.reset();
        dataBuffer.writeVarInt(p.opCode());
        p.writePacket(dataBuffer);
        dataBuffer.flush();
        writeVarInt(byteBuffer.size());
        write(byteBuffer.toByteArray());
    }
}
