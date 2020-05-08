package zedly.zbot.network.packet.clientbound;

import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import java.util.UUID;

public class Packet0CBossBar implements ClientBoundPacket {

    UUID uuid;
    private int action;
    private String title;
    private float health;
    private int color;
    private int division;
    private int flags;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        uuid = dis.readUUID();
        action = dis.readVarInt();
        switch (action) {
            case 0:
                title = dis.readString();
                health = dis.readFloat();
                color = dis.readVarInt();
                division = dis.readVarInt();
                flags = dis.readUnsignedByte();
                break;
            case 2:
                health = dis.readFloat();
                break;
            case 3:
                title = dis.readString();
                break;
            case 4:
                color = dis.readVarInt();
                division = dis.readVarInt();
                break;
            case 5:
                flags = dis.readUnsignedByte();
        }
    }
}
//Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture