package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

public class Packet1FOpenHorseWindow implements ClientBoundPacket {
    
    private int windowId;
    private int numSlots;
    private int entityId;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int clen) throws IOException {
        windowId = dis.readUnsignedByte();
        numSlots = dis.readVarInt();
        entityId = dis.readInt();
    }

}