package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;

public class Packet1DRecipeBookData implements ServerBoundPacket {

    private final int type;
    private final boolean craftingRecipeBookOpen, craftingRecipeFilterActive,
            smeltingRecipeBookOpen, smeltingRecipeFilterActive,
            blastingRecipeBookOpen, blastingRecipeFilterActive,
            smokingRecipeBookOpen, smokingRecipeFilterActive;
    private final String recipeId;

    public Packet1DRecipeBookData(String recipeId,
            boolean blastingRecipeBookOpen, boolean blastingRecipeFilterActive,
            boolean smokingRecipeBookOpen, boolean smokingRecipeFilterActive) {
        this.type = 0;
        this.recipeId = recipeId;
        this.craftingRecipeBookOpen = false;
        this.craftingRecipeFilterActive = false;
        this.smeltingRecipeBookOpen = false;
        this.smeltingRecipeFilterActive = false;
        this.blastingRecipeBookOpen = blastingRecipeBookOpen;
        this.blastingRecipeFilterActive = blastingRecipeFilterActive;
        this.smokingRecipeBookOpen = smokingRecipeBookOpen;
        this.smokingRecipeFilterActive = smokingRecipeFilterActive;
    }

    public Packet1DRecipeBookData(boolean craftingRecipeBookOpen, boolean craftingRecipeFilterActive,
            boolean smeltingRecipeBookOpen, boolean smeltingRecipeFilterActive,
            boolean blastingRecipeBookOpen, boolean blastingRecipeFilterActive,
            boolean smokingRecipeBookOpen, boolean smokingRecipeFilterActive) {
        this.type = 1;
        this.recipeId = null;
        this.craftingRecipeBookOpen = craftingRecipeBookOpen;
        this.craftingRecipeFilterActive = craftingRecipeFilterActive;
        this.smeltingRecipeBookOpen = smeltingRecipeBookOpen;
        this.smeltingRecipeFilterActive = smeltingRecipeFilterActive;
        this.blastingRecipeBookOpen = blastingRecipeBookOpen;
        this.blastingRecipeFilterActive = blastingRecipeFilterActive;
        this.smokingRecipeBookOpen = smokingRecipeBookOpen;
        this.smokingRecipeFilterActive = smokingRecipeFilterActive;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(type);
        if (type == 0) {
            dos.writeString(recipeId);
        } else {
            dos.writeBoolean(craftingRecipeBookOpen);
            dos.writeBoolean(craftingRecipeFilterActive);
            dos.writeBoolean(smeltingRecipeBookOpen);
            dos.writeBoolean(smeltingRecipeFilterActive);
        }
        dos.writeBoolean(blastingRecipeBookOpen);
        dos.writeBoolean(blastingRecipeFilterActive);
        dos.writeBoolean(smokingRecipeBookOpen);
        dos.writeBoolean(smokingRecipeFilterActive);
    }

    @Override
    public int opCode() {
        return 0x1D;
    }

}

// This packet has never been implemented. If you find this in a stack trace: Congratulations, you played yourself
