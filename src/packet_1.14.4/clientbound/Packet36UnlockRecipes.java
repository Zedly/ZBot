package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.StringUtil;
import zedly.zbot.network.ExtendedDataInputStream;

public class Packet36UnlockRecipes implements ClientBoundPacket {

    // Fuck this packet I know how to craft
    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        int mode = dis.readVarInt();
        boolean CRBO = dis.readBoolean();
        boolean CRBFA = dis.readBoolean();
        boolean SRBO = dis.readBoolean();
        boolean SRBFA = dis.readBoolean();
        int arraysize1 = dis.readVarInt();
        String[] RecipeIDs1 = new String[arraysize1];
        for (int i = 0; i < arraysize1; i++) {
            RecipeIDs1[i] = dis.readString();
        }
        if (mode == 0) {
            int arraysize2 = dis.readVarInt();
            String[] RecipeIDs2 = new String[arraysize2];
            for (int i = 0; i < arraysize2; i++) {
                RecipeIDs2[i] = dis.readString();
            }
        }
    }
}
