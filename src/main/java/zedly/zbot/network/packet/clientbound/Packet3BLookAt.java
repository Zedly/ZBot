package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

public class Packet3BLookAt implements ClientBoundPacket {

    private int feetEyes;
    private double targetX, targetY, targetZ;
    private boolean isEntity;
    private int entityId;
    private int entityFeetEyes;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        feetEyes = dis.readVarInt();
        targetX = dis.readDouble();
        targetY = dis.readDouble();
        targetZ = dis.readDouble();
        isEntity = dis.readBoolean();
        if (isEntity) {
            entityId = dis.readVarInt();
            entityFeetEyes = dis.readVarInt();
        }
    }

}
//Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture