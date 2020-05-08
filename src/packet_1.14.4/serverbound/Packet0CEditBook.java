package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.inventory.ItemStack;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* */

public class Packet0CEditBook implements ServerBoundPacket {
    private final ItemStack newbook;
    private final boolean issigning;  // True if the player is signing the book; false if the player is saving a draft.
    private final int hand;  // 0: Main hand, 1: Off hand


    public Packet0CEditBook(ItemStack newbook, boolean issigning, int hand) {
        this.newbook = newbook;
        this.issigning = issigning;
        this.hand = hand;
    }

    @Override
    public int opCode() {
        return 0x0C;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeSlot(newbook);
        dos.writeBoolean(issigning);
        dos.writeVarInt(hand);
    }
}
