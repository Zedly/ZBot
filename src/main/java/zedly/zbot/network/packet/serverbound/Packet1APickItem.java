package   zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* Used to swap out an empty space on the hotbar with the item in the given inventory slot.  The Notchain client uses this for pick block functionality (middle click) to retrieve items from the inventory.
*/


/**
* Used to swap out an empty space on the hotbar with the item in the given inventory slot.  The Notchian client uses this for pick block functionality (middle click) to retrieve items from the inventory.
*/

public class Packet1APickItem implements ServerBoundPacket {
    private final int slottouse;  // See <a href="/Inventory" title="Inventory">Inventory</a>.


    public Packet1APickItem(int slottouse) {
        this.slottouse = slottouse;
    }

    @Override
    public int opCode() {
        return 0x1A;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(slottouse);
    }
}
