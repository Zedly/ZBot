package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.lang3.tuple.Triple;
import zedly.zbot.GameContext;
import zedly.zbot.network.ExtendedDataInputStream;

public class Packet0DChunkBiomes implements ClientBoundPacket {

    private ArrayList<Triple<Integer, Integer, byte[]>> biomeData = new ArrayList<>();

    public void readPacket(ExtendedDataInputStream dis) throws IOException {
        int numChunks = dis.readVarInt();

        for (int c = 0; c < numChunks; c++) {
            int chunkX = dis.readInt();
            int chunkZ = dis.readInt();
            int dataLen = dis.readVarInt();
            byte[] biomeBytes = new byte[dataLen];
            dis.readFully(biomeBytes);
            biomeData.add(Triple.of(chunkX, chunkZ, biomeBytes));
        }
    }
    
    public void process(GameContext context) {
        for(Triple<Integer, Integer, byte[]> t : biomeData) {
            context.getSelf().getEnvironment().reloadChunkBiomes(t.getLeft(), t.getMiddle(), t.getRight());
        }        
    }
}
