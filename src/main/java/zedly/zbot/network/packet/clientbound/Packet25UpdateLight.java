package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.ArrayList;
import zedly.zbot.network.ExtendedDataInputStream;

public class Packet25UpdateLight implements ClientBoundPacket {

    private int chunkX;
    private int chunkZ;
    private int skyLightMask;
    private int blockLightMask;
    private int emptySkyLightMask;
    private int emptyBlockLightMask;
    private ArrayList<byte[]> skyLightArrays = new ArrayList<>();
    private ArrayList<byte[]> blockLightArrays = new ArrayList<>();

    @Override
    public void readPacket(ExtendedDataInputStream dis, int clen) throws IOException {
        chunkX = dis.readVarInt();
        chunkZ = dis.readVarInt();
        skyLightMask = dis.readVarInt();
        blockLightMask = dis.readVarInt();
        emptySkyLightMask = dis.readVarInt();
        emptyBlockLightMask = dis.readVarInt();

        for (int i = 0; i < Integer.bitCount(skyLightMask); i++) {
            int arrayLength = dis.readVarInt();
            byte[] lightValues = new byte[arrayLength];
            dis.readFully(lightValues);
        }
        for (int i = 0; i < Integer.bitCount(blockLightMask); i++) {
            int arrayLength = dis.readVarInt();
            byte[] lightValues = new byte[arrayLength];
            dis.readFully(lightValues);
        }
    }

}
// Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture