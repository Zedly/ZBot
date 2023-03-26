package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataOutputStream;

public class Packet10Interact implements ServerBoundPacket {

    int targetId;
    int clickType;
    int hand;
    Location loc;

    public Packet10Interact(int targetId, int hand) {
        this.targetId = targetId;
        this.hand = hand;
        this.clickType = 0;
    }

    public Packet10Interact(int targetId) {
        this.targetId = targetId;
        this.clickType = 1;
    }

    public Packet10Interact(int targetId, Location loc, int hand) {
        this.targetId = targetId;
        this.clickType = 2;
        this.loc = loc;
        this.hand = hand;
    }

    @Override
    public int opCode() {
        return 0x10;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(targetId);
        dos.writeVarInt(clickType);
        if (clickType == 2) {
            dos.writeFloat((float) loc.getX());
            dos.writeFloat((float) loc.getY());
            dos.writeFloat((float) loc.getZ());
        }
        if (clickType != 1) {
            dos.writeVarInt(hand);
        }
    }
}
