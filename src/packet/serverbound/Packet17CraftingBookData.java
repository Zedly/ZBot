package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;

public class Packet17CraftingBookData implements ServerBoundPacket {

    private final int type;
    private final int recipeID;
    private final boolean craftingBookOpen;
    private final boolean craftingFilter;
    
    public Packet17CraftingBookData(int recipeID) {
        this.type = 0;
        this.recipeID = recipeID;
        this.craftingBookOpen = false;
        this.craftingFilter = false;
    }
    
    public Packet17CraftingBookData(boolean craftingBookOpen, boolean craftingFilter) {
        this.type = 1;
        this.craftingBookOpen = craftingBookOpen;
        this.craftingFilter = craftingFilter;
        this.recipeID = 0;
    }
    
    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(type);
        if(type == 0) {
            dos.writeInt(recipeID);
        } else {
            dos.writeBoolean(craftingBookOpen);
            dos.writeBoolean(craftingFilter);
        }
    }

    @Override
    public int opCode() {
        return 0x17;
    }

}
//Skeleton with unreadable structure and no ancestor. Implement manually
