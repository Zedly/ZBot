package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.event.block.BlockChangeEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

public class Packet0BBlockChange implements ClientBoundPacket {

    Location loc;
    private int blockID;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        loc = dis.readPosition();
        blockID = dis.readVarInt();
    }

    @Override
    public void process(GameContext context) {
        context.getSelf().getEnvironment().setBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), blockID >> 4, blockID & 0xF);
        context.getMainThread().fireEvent(new BlockChangeEvent(loc, blockID));
    }
}
