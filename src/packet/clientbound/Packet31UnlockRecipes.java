package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.network.ExtendedDataInputStream;

public class Packet31UnlockRecipes implements ClientBoundPacket {

    private int action;
    private boolean craftingBookOpen;
    private boolean filteringCraftable;
    private int arraySize1;
    private int[] recipeIDs;
    private int arraySize2;
    private int[] recipeIDs2;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        action = dis.readVarInt();
        craftingBookOpen = dis.readBoolean();
        filteringCraftable = dis.readBoolean();
        arraySize1 = dis.readVarInt();
        recipeIDs = new int[arraySize1];
        for (int i = 0; i < arraySize1; i++) {
            recipeIDs[i] = dis.readVarInt();
        }
        if (action == 0) {
            arraySize2 = dis.readVarInt();
            recipeIDs2 = new int[arraySize1];
            for (int i = 0; i < arraySize1; i++) {
                recipeIDs2[i] = dis.readVarInt();
            }
        }
    }
}
//Skeleton with unreadable structure and no ancestor. Implement manually
