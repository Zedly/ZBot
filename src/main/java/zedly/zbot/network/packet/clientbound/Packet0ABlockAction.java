package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.api.event.block.BlockActionEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

public class Packet0ABlockAction implements ClientBoundPacket {

    private Location loc;
    private int blockType;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        loc = dis.readPosition();
        dis.readUnsignedByte();
        dis.readUnsignedByte();
        blockType = dis.readVarInt();
    }

    @Override
    public void process(GameContext context) {
        context.getMainThread().fireEvent(new BlockActionEvent(loc, blockType));
    }
}
